package com.capsule.domain.fcm.model;

import com.capsule.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Fcm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "token")
    private String token;


    public Fcm(String token, Member member) {
        this.token = token;
        this.member = member;
    }

    public void updateToken(String token){
        this.token = token;
    }
}
