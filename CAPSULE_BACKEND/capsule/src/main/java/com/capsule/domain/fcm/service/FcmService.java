package com.capsule.domain.fcm.service;

import com.capsule.domain.fcm.model.Fcm;
import com.capsule.domain.fcm.message.FcmMessage;
import com.capsule.domain.fcm.repository.FcmRepository;
import com.capsule.domain.fcm.requestDto.FcmRequestDto;
import com.capsule.domain.member.model.Member;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import com.capsule.global.security.auth.MemberDetails;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FcmService {

    private final FcmRepository fcmRepository;

    @Transactional
    public void saveFcm(Authentication authentication, FcmRequestDto requestDto){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        if(fcmRepository.existsByMember(member)){
            Fcm fcm = fcmRepository.findByMember(member).orElseThrow(()->{
                throw new ErrorResponse(ExceptionMessage.NOT_FOUND_FCM);
            });
            fcm.updateToken(requestDto.getToken());
            fcmRepository.save(fcm);
            return;
        }
        Fcm fcm = new Fcm(requestDto.getToken(), member);
        fcmRepository.save(fcm);
    }

    public String getFcmToken(Member member){
        Fcm fcm = fcmRepository.findByMember(member).orElseThrow(() -> {
            throw new ErrorResponse(ExceptionMessage.NOT_FOUND_FCM);
        });

        return fcm.getToken();
    }

    public void sendFcm(FcmMessage fcmMessage) {
        Message message = Message.builder()
                .setWebpushConfig(WebpushConfig.builder()
                        .setNotification(WebpushNotification.builder()
                                .setTitle(fcmMessage.getTitle())
                                .setBody(fcmMessage.getMessage())
                                .build())
                        .build())
                .setToken(fcmMessage.getToken())
                .build();

        try{
            FirebaseMessaging.getInstance().sendAsync(message).get();
        }catch (ExecutionException e){
            log.info(e.getMessage());
        }catch (InterruptedException e){
            log.info(e.getMessage());
        }
    }

    public void deleteToken(Authentication authentication){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        fcmRepository.deleteByMember(member);
    }

    public void sendFriend(Member member, Member friend){
        if(!fcmRepository.existsByMember(friend)) return;

        Fcm fcm = fcmRepository.findByMember(friend).orElseThrow(()->{
            throw new ErrorResponse(ExceptionMessage.NOT_FOUND_FCM);
        });

        FcmMessage fcmMessage = FcmMessage.sendFriendRequestMessage(member,fcm.getToken());
        sendFcm(fcmMessage);
    }
}
