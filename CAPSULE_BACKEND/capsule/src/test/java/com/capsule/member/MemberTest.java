package com.capsule.member;

import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.model.Role;
import com.capsule.global.exception.ErrorResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @Test
    @DisplayName("유저 생성 테스트")
    public void 유저_생성_테스트(){
        Member member = Member.createMember("test@123","test","test","01011111111",Role.ROLE_USER);
        Assertions.assertThat(member.getMemberRoles().size()).isEqualTo(1);
    }
    @Test
    @DisplayName("유저 role 없음 예외 테스트")
    public void 유저_role_예외_테스트(){
        Member member = Member.createMember("test@123","test","test","01011111111",null);
        Assertions.assertThatThrownBy(()-> member.getMemberRoles()).isInstanceOf(ErrorResponse.class);
    }

}
