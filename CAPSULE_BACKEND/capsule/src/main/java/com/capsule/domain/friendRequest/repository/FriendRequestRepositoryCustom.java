package com.capsule.domain.friendRequest.repository;

import com.capsule.domain.member.model.Member;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepositoryCustom {

    List<Member> findSendRequestMemberSuccess(long memberId, boolean success);
}
