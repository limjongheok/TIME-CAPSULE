package com.capsule.domain.fcm.message;

import com.capsule.domain.capsule.model.Capsule;
import com.capsule.domain.member.model.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FcmMessage {

    private String title;
    private String message;
    private String token;

    private FcmMessage(String title, String message, String token) {
        this.title = title;
        this.message = message;
        this.token = token;
    }

    public static FcmMessage createFcmMessage(Capsule capsule,String token){
        String title = capsule.getTitle() + "이 공개되었습니다.";
        String message = "친구들과의 추억을 확인해 보세요";
        return new FcmMessage(title,message,token);

    }

    public static FcmMessage sendFriendRequestMessage(Member member , String token){
        String title = member.getName() + "에게 친구 신청이 왔어요";
        String message = "확인해 주세요!!";
        return  new FcmMessage(title,message,token);
    }

}
