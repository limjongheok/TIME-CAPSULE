package com.capsule.domain.member.repository;

import com.capsule.domain.member.model.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findMembersEmailAndState(String email);
    List<Member> findAll();
}
