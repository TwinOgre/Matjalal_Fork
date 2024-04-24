package com.proj.Matjalal.domain.member.repository;


import com.proj.Matjalal.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findByEmail(String email);
}
