package com.capsule.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    //ERRORCODE 400
    NOT_EXIST_ROLE_EXCEPTION(400,"NotExistRoleException","권한이 존재하지 않습니다."),
    NOT_EXIST_REGISTRATIONID(400,"NotExistRegistrationId","연동되지 않은 간편 회원가입 입니다."),
    NOT_EXIST_MEMBERBYEMAIL(400,"NotExistMemberByEmail","해당 이메일에 존재하는 유저가 없습니다."),
    DUPLICATED_EMAIL_EXCEPTION(400,"DuplicatedEmailException","이메일이 이미 존재합니다."),
    NOT_MATCH_AUTHCODE(400,"NotMathAuthCode","인증코드가 일치하지 않습니다."),
    NOT_SUCCESS_EMAIL(400,"NotSuccessEmail","인증이 되지 않은 이메일 입니다."),
    NOT_FOUND_MEMBER(400,"NotFoundMember","해당맴버를 찾을 수 없습니다."),
    NOT_FOUND_FCM(400,"NotFoundFCM","해당푸시알림 토큰을 찾을 수 없습니다."),
    NOT_FOUND_FRIEND(400,"NotFoundFriend","친구를 찾을 수 없습니다."),
    NOT_FOUND_FAVORITE(400,"NotFoundFavorite","좋아요를 찾을 수 없습니다."),
    NOT_FOUND_ROOM(400,"NotFoundRoom","방을 찾을 수 없습니다."),
    NOT_FOUND_FRIEND_REQUEST(400,"NotFoundFriendRequest","해당 친구요청이 존재하지 않습니다."),
    EXIST_FRIEND(400,"ExistFriend","해당 친구가 존재합니다."),
    EXIST_MAIL(400,"ExistMAIL","해당 메일이 존재합니다."),
    EXIST_ROOM_MEMBER(400,"ExistRoomMember","해당 방에 이미 유저가 존재합니다."),
    EXIST_FRIEND_REQUEST(400,"ExistFriend_Request","해당 친구요청이 존재합니다."),
    EXIST_AIIMG_REQUEST(400,"ExistAIIMG_Request","해당 이미지가 존재합니다."),
    DUPLICATED_FAVORITE(400, "DuplicatedFavoriteException", "좋아요가 이미 존재합니다."),

    //인증 에러 401
    EXPIREDJWTEXCEPTION(401,"ExpiredJwtException","토큰이 만료했습니다."),
    NOTVALIDJWTEXCEPTION(401,"NotValidJwtException","토큰이 유효하지 않습니다."),
    NOTAUTHENTICATIONEXCEPTION(401,"Not_Authentication_Exception","인증되지 않은 사용자 입니다."),

    //접근 권한 에러
    CANNOT_READ(403,"CanNotRead","읽을수 없습니다."),
    CANNOT_WRITE(403,"CanNotWRITE","쓸수 없습니다.");

    private int statusNum;
    private String errorCode;
    private String errorMessage;

    ExceptionMessage(int statusNum, String errorCode, String errorMessage){
        this.statusNum = statusNum;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
