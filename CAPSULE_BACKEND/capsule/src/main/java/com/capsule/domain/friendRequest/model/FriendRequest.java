package com.capsule.domain.friendRequest.model;

import com.capsule.domain.member.model.Member;
import com.capsule.global.baseTimeEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class FriendRequest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_request_id")
    private long friendRequestId;

    @Column(name = "friend_id")
    private long friendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "success", nullable = false)
    private boolean success;

    private FriendRequest(long friendId, Member member, boolean success){
        this.friendId = friendId;
        this.member = member;
        this.success = success;
    }

    public static FriendRequest createFriendRequest(long friendId, Member member, boolean success){
        return new FriendRequest(friendId,member,success);
    }

    public void acceptRequest(){
        this.success = true;
    }
}
