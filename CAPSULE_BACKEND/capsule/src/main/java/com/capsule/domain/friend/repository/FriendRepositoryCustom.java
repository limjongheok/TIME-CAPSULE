package com.capsule.domain.friend.repository;

import com.capsule.domain.member.model.Member;

import java.util.List;

public interface FriendRepositoryCustom {
    List<Member> findAllByFriends(long memberId);
    List<Member> findFriendsByEmail(long memberId, String email);
}
