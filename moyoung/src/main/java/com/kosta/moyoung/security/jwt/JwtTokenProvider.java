package com.kosta.moyoung.security.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.kosta.moyoung.member.dto.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	
	
//    @Value("jwt.secret")
//    private String secretKey;
    private final Key key;
    private static final long ACCESS_TOKEN_EXPIRE_TIME =  60 * 30 * 1000L;  // 토큰 유효시간 : 30분
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 60 * 60 * 24 * 7*1000L ;
	
	private static final String AUTHORITIES_KEY = "auth";	
    private static final String BEARER_TYPE = "Bearer";
    

//	@PostConstruct
//	protected void init() {
//		log.info("INIT : JWT SecretKey 초기화 시작");
//		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//		log.info("INIT : JWT SecretKey 초기화 완료");
//	}
	
	 public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
			log.info("INIT : JWT SecretKey 초기화 시작");
	        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
	        this.key = Keys.hmacShaKeyFor(keyBytes);
	        log.info("INIT : JWT SecretKey 초기화 완료");
	    }

//	public long getMemberId(String token) {
//		log.info("[getUserName] 토큰에서 회원 ID 추출 ");
//		long memberId = Long.parseLong(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject());
//		log.info("[getUserName] 토큰에서 회원 ID 추출 완료 iD : {}" ,memberId);
//		return memberId;
//	}
	
	

	// 토큰 생성
	public TokenDto generateTokenDto(Authentication authentication) {
        // 권한들 가져오기
		log.info("[권한들 가져오기]" , authentication);
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        long now = (new Date()).getTime();

        // Access Token 생성
        log.info("[Access Token 생성]"  );
        
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();
        
        log.info("[Access Token 완료]" , accessToken);
        
        // Refresh Token 생성
        log.info("[Refresh Token 생성]"  );
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        log.info("[Refresh Token 완료]" +  refreshToken);
        
        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }
	
	
	// 토큰으로부터 유저정보를 가져온다.
	   public Authentication getAuthentication(String accessToken) {
		   
	        // 토큰 복호화
		   log.info("[getAuthentication] 토큰 인증 정보 조회 시작");
	        Claims claims = parseClaims(accessToken);

	        if (claims.get(AUTHORITIES_KEY) == null) {
	            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
	        }

	        // 클레임에서 권한 정보 가져오기
	        Collection<? extends GrantedAuthority> authorities =
	                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
	                        .map(SimpleGrantedAuthority::new)
	                        .collect(Collectors.toList());

	        // UserDetails 객체를 만들어서 Authentication 리턴
	        UserDetails principal = new User(claims.getSubject(), "", authorities);

	        log.info("[getAuthentication] 토큰 인증 정보 조회 완료 member : {}", principal.toString());
	        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	    }
	
	
	public String resolveToken(HttpServletRequest request) {
		 log.info("[resolveToken] 토큰 정보 헤더에서 읽어오기 ");
		return request.getHeader("Authorization");
	}
	
	// 토큰의 유효성+만료시간 확인
	public boolean validateToken(String token) {
		log.info("[valiateToken] 토큰 유효성 검증");
		try {
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
		return false;
	}
	
	private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
            			.setSigningKey(key)
            			.build()
            			.parseClaimsJws(accessToken)
            			.getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

	public Long getMemberId(String accessToken) {
        log.info("[getMemberId] 토큰에서 회원 ID 추출 시작");
        Claims claims = parseClaims(accessToken);
        Long memberId = Long.parseLong(claims.getSubject());
        log.info("[getMemberId] 토큰에서 회원 ID 추출 완료. ID: {}", memberId);
        return memberId;
    }
	
}