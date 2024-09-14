package com.capsule.domain.friend.repository;

import com.capsule.domain.friend.model.Friend;
import com.capsule.domain.member.model.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Long>, FriendRepositoryCustom {

    boolean existsByFriendIdAndMember_Id(long friendId, long memberId);

    @Override
    List<Member> findAllByFriends(long memberId);

    @Override
    List<Member> findFriendsByEmail(long memberId, String email);
}
