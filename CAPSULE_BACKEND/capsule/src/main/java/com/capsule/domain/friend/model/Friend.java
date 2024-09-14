package com.capsule.domain.friend.model;

import com.capsule.domain.member.model.Member;
import com.capsule.global.baseTimeEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Friend extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "friend_id")
    private long friendId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Friend(long friendId, Member member){
        this.friendId = friendId;
        this.member = member;
    }

    public static Friend createFriend(long friendId, Member member){
        return new Friend(friendId,member);
    }

}
