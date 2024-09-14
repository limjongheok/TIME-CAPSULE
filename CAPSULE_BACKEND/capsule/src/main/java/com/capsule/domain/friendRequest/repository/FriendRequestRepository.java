package com.capsule.domain.friendRequest.repository;

import com.capsule.domain.friendRequest.model.FriendRequest;
import com.capsule.domain.member.model.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long>, FriendRequestRepositoryCustom {

    boolean existsByFriendIdAndMember_Id(long friendId, long memberId);
    Optional<FriendRequest> findByFriendIdAndMember_IdAndSuccess(long friendId, long memberId,boolean success);

    @EntityGraph(attributePaths = {"member"})
    List<FriendRequest> findByFriendIdAndSuccess(long friendId,boolean success);

    @Override
    List<Member> findSendRequestMemberSuccess(long memberId, boolean success);
}
