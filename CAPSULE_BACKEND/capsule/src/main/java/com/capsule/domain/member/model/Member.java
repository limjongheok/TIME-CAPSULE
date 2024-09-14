package com.capsule.domain.member.model;

import com.capsule.global.baseTimeEntity.BaseTimeEntity;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "img_url", nullable = true)
    private String imgUrl;

    @Column(name = "phonenumber",nullable = false, length = 13)
    private String phoneNumber;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "state", nullable = false)
    private boolean state;


    @Builder
    private Member(String email, String password, String name, String phoneNumber, Role role, boolean state) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.imgUrl = DefaultImg.DEFAULT_IMG.img;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.state = state;
    }


    public static Member createMember(String email, String name, String phoneNumber, Role role){
        return Member.builder()
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .role(role)
                .state(true)
                .build();
    }

    public static Member createMember(String email,String password, String name , String phoneNumber, Role role){
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .role(role)
                .state(true)
                .build();
    }

    public List<Role> getMemberRoles(){
        if(this.role != null){
            return Arrays.asList(this.role);
        }
        throw new ErrorResponse(ExceptionMessage.NOT_EXIST_ROLE_EXCEPTION);
    }

    public void deleteMember(){
        this.state = false;
    }
    public void changeName(String name){this.name = name;}
    public void changePhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}
    public void changeImgUrl(String imgUrl){this.imgUrl = imgUrl;}
}
