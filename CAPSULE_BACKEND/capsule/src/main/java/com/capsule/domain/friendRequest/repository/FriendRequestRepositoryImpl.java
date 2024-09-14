package com.capsule.domain.friendRequest.repository;

import com.capsule.domain.friendRequest.model.QFriendRequest;
import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.model.QMember;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class FriendRequestRepositoryImpl implements FriendRequestRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private QMember member = QMember.member;
    private QFriendRequest friendRequest = QFriendRequest.friendRequest;

    public FriendRequestRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Member> findSendRequestMemberSuccess(long memberId, boolean success) {
        var subQuery = JPAExpressions
                .select(friendRequest.friendId)
                .from(friendRequest)
                .where(friendRequest.member.id.eq(memberId).and(friendRequest.success.isFalse()));
        List<Member> results = jpaQueryFactory
                .select(member)
                .from(member)
                .where(member.id.in(subQuery))
                .fetch();
        return results;
    }
}
