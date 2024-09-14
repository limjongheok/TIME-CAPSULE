package com.capsule.domain.memberCapsule.model;

import com.capsule.domain.member.model.Member;
import com.capsule.domain.capsule.model.Capsule;
import com.capsule.global.baseTimeEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MemberCapsule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capsule_id")
    private Capsule capsule;

    @Column(name = "read_check")
    @Enumerated(EnumType.STRING)
    private Read readCheck;

    @Column(name = "write_check")
    private boolean writeCheck;

    @Builder
    private MemberCapsule(Member member, Capsule capsule, Read readCheck, boolean writeCheck) {
        this.member = member;
        this.capsule = capsule;
        this.readCheck = readCheck;
        this.writeCheck = writeCheck;
    }

    public static MemberCapsule createMemberRoom(Member member, Capsule capsule, Read readCheck, boolean writeCheck){
        return MemberCapsule.builder()
                .member(member)
                .capsule(capsule)
                .readCheck(readCheck)
                .writeCheck(writeCheck)
                .build();
    }

    public void readAble(){
        this.readCheck = Read.READ_ABLE;
    }

    public void readComplete(){
        this.readCheck = Read.READ_COMPLETE;
    }

    public void write(){
        this.writeCheck = true;
    }
}
