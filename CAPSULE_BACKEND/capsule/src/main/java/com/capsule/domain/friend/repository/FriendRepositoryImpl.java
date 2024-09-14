package com.capsule.domain.friend.repository;

import com.capsule.domain.friend.model.QFriend;
import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.model.QMember;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class FriendRepositoryImpl implements FriendRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QMember member = QMember.member;
    private QFriend friend = QFriend.friend;

    public FriendRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Member> findAllByFriends(long memberId) {
        var subQuery = JPAExpressions
                .select(friend.friendId)
                .from(friend)
                .where(friend.member.id.eq(memberId));

        List<Member> results = jpaQueryFactory
                .select(member) // 메인 쿼리에서 선택할 엔티티
                .from(member) // 메인 쿼리의 시작 엔티티
                .where(member.id.in(subQuery)).fetch();
        return results;
    }

    @Override
    public List<Member> findFriendsByEmail(long memberId, String email) {
        var subQuery = JPAExpressions
                .select(friend.friendId)
                .from(friend)
                .where(friend.member.id.eq(memberId));

        List<Member> results = jpaQueryFactory
                .select(member) // 메인 쿼리에서 선택할 엔티티
                .from(member) // 메인 쿼리의 시작 엔티티
                .where(member.id.in(subQuery).and(member.email.like(email+"%"))).fetch();
        return results;
    }
}
