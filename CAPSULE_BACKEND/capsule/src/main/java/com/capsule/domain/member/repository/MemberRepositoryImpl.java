package com.capsule.domain.member.repository;

import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.model.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private QMember member = QMember.member;

    public MemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    private BooleanExpression eqEmail(String email){
        if(StringUtils.isEmpty(email)){
            return null;
        }
        return member.email.like(email + "%");
    }

    @Override
    public List<Member> findMembersEmailAndState(String email) {
        return (List<Member>) jpaQueryFactory.from(member).where(member.state.isTrue().and(eqEmail(email))).fetch();
    }

    @Override
    public List<Member> findAll() {
        return (List<Member>) jpaQueryFactory.from(member).where(member.state.isTrue()).fetch();
    }
}
