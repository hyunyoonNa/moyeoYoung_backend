package com.kosta.moyoung.openroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.moyoung.openroom.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
