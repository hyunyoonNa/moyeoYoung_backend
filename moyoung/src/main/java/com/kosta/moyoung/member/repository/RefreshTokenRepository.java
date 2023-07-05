package com.kosta.moyoung.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.moyoung.member.entity.RefreshToken;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
   Optional<RefreshToken> findByKey(String key);
  
}
