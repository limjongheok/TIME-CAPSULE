package com.capsule.domain.member.repository;

import com.capsule.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> , MemberRepositoryCustom{

    boolean existsByEmailAndState(String email,boolean state);
    Optional<Member> findByEmailAndState(String email,boolean state);

    @Override
    List<Member> findMembersEmailAndState(String email);

    @Override
    List<Member> findAll();

}
