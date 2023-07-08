package com.kosta.moyoung.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.kosta.moyoung.security.jwt.JwtAccessDeniedHandler;
import com.kosta.moyoung.security.jwt.JwtAuthenticationEntryPoint;
import com.kosta.moyoung.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
	
	private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            // CSRF 설정 Disable
        http.csrf().disable()

            // exception handling 할 때 우리가 만든 클래스를 추가
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            
            // 시큐리티는 기본적으로 세션을 사용
            // 여기서는 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
            .and()
            .authorizeRequests() 
            .antMatchers("/**/**").permitAll()
            .antMatchers("/room/**").permitAll() 
            .antMatchers("/feed/**").permitAll() 
            .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요
            
            .and()
            .logout()
            .logoutSuccessUrl("/auth/login")
            
            .and()
            .oauth2Login()
            
            .and()
            .apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
//        .formLogin()
//        	.loginPage("/auth/login")
//            .usernameParameter("email")
//            .passwordParameter("password")
//            .defaultSuccessUrl("/")
//		 // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
//            
//        .and()
//        .apply(new JwtSecurityConfig(jwtTokenProvider));
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		return http.build();
	}
}
