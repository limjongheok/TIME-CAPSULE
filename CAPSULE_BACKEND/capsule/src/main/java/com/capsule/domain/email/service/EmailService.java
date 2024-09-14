package com.capsule.domain.email.service;

import com.capsule.domain.email.repository.EmailRepository;
import com.capsule.domain.email.requestDto.CheckAuthCodeRequestDto;
import com.capsule.domain.email.requestDto.SendAuthCodeRequestDto;
import com.capsule.domain.member.model.Member;
import com.capsule.domain.member.repository.MemberRepository;
import com.capsule.domain.capsule.model.Capsule;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EmailService {

    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;

    public void sendAuthenticationCode(SendAuthCodeRequestDto sendAuthCodeRequestDto) throws MessagingException {
        String email = sendAuthCodeRequestDto.getEmail();

        if(memberRepository.existsByEmailAndState(email,true))
            throw new ErrorResponse(ExceptionMessage.DUPLICATED_EMAIL_EXCEPTION);

        String authCode = createAuthCode();
        log.info(authCode);

        emailRepository.saveAuth(email,authCode);

        MimeMessage emailForm = createMessage(email, authCode);
        javaMailSender.send(emailForm);
    }

    public void sendRoomMessage(Member member, Capsule room)throws MessagingException{;
        MimeMessage emailForm = createReadRoomMessage(member, room);
        javaMailSender.send(emailForm);
    }

    public void checkAuthCode(CheckAuthCodeRequestDto checkAuthCodeRequestDto){
        String email = checkAuthCodeRequestDto.getEmail();
        String authCode = checkAuthCodeRequestDto.getAuthCode();
        if (!emailRepository.checkAuth(email, authCode)) {
            log.error("false auth");
            throw new ErrorResponse(ExceptionMessage.NOT_MATCH_AUTHCODE);
        }

        emailRepository.successAuth(email);
    }

    private String createAuthCode(){
        Random random = new Random();
        StringBuffer code = new StringBuffer();

        for(int i = 0; i < 8; i++){
            int index = random.nextInt(3);
            switch(index){
                case 0:
                    code.append((char) (random.nextInt(26) + 97));
                    break;
                case 1:
                    code.append((char)(random.nextInt(26) + 65));
                    break;
                case 2:
                    code.append((char)(random.nextInt(26) + 48));
                    break;
            }
        }
        return code.toString();
    }

    private MimeMessage createMessage(String receiver, String authCode) throws MessagingException {
        log.info("sender: " + receiver);
        log.info("authCode: " + authCode);

        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, receiver);
        message.setSubject("회원가입 이메일 인증");// 제목
        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += authCode + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("jjongbbang2@naver.com"));// 보내는 사람

        return message;
    }

    private MimeMessage createReadRoomMessage(Member member, Capsule capsule) throws MessagingException {
        log.info("보내는 대상 : " + member.getEmail());
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, member.getEmail());
        message.setSubject("[타임캡슐 메일] " + capsule.getTitle());// 제목
        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h2>안녕하세요, "+member.getName()+"</h2>";
        msgg += "<p>오늘은 특별한 날입니다! </p>";
        msgg += "<p>[메일을 보낸 날짜]: "+capsule.getCreateDate().toLocalDate()+"에 당신은 자신의 미래에게 메일을 보내기 위해 타임캡슐을 사용했습니다. </p>";
        msgg += "<p>그 시간이 지나, 오늘 이 메일을 받게 되었습니다. </p>";
        msgg += "<div>";
        msgg += "<h3 style='font-size:130%;'>당신이 과거에 보낸 메시지는 다음과 같습니다 </h3>";
        msgg += "<a href = https://capsuletalk.site/main/"+" style='color:blue; font-size:130%;'> https://capsuletalk.site/main"+"</a>" ;
        msgg += "<div><br/> ";
        msgg += "<p>과거의 당신이 오늘의 나에게 하고 싶었던 말을 기억하며, 지금 이 순간을 즐기시길 바랍니다. 과거의 꿈과 현재의 현실 사이에서 어떤 변화가 있었는지 돌아보는 시간이 되었으면 합니다. </p>";
        msgg += "<p>행복한 하루 되세요!</p><br/>";
        msgg += "<p>감사합니다,</p>";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("jjongbbang2@naver.com"));// 보내는 사람

        return message;
    }
}
