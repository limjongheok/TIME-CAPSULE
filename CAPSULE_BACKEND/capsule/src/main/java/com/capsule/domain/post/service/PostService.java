package com.capsule.domain.post.service;

import com.capsule.domain.capsule.model.Capsule;
import com.capsule.domain.capsule.repository.CapsuleRepository;
import com.capsule.domain.member.model.Member;
import com.capsule.domain.memberCapsule.model.MemberCapsule;
import com.capsule.domain.memberCapsule.model.Read;
import com.capsule.domain.memberCapsule.repository.MemberCapsuleRepository;
import com.capsule.domain.post.message.ResponseDto.PostsResponseDto;
import com.capsule.domain.post.model.Post;
import com.capsule.domain.post.repository.PostRepository;
import com.capsule.domain.post.requestDto.WritePostRequestDto;
import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import com.capsule.global.security.auth.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final MemberCapsuleRepository memberCapsuleRepository;

    @Transactional
    public void writePost(Authentication authentication, WritePostRequestDto writePostRequestDto, long capsuleId){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        MemberCapsule memberCapsule = memberCapsuleRepository.findByMemberAndCapsule_Id(member,capsuleId).orElseThrow(() ->
                new ErrorResponse(ExceptionMessage.CANNOT_WRITE));

        Capsule capsule = memberCapsule.getCapsule();
        if(memberCapsule.isWriteCheck()){ // 이미 써진것
            throw new ErrorResponse(ExceptionMessage.CANNOT_WRITE);
        }

        if(!capsule.isWriteAble()){ // 쓸 수 없는것
            throw new ErrorResponse(ExceptionMessage.CANNOT_WRITE);
        }

        Post post = Post.createPost(writePostRequestDto.getImgUrl(), writePostRequestDto.getMemo(),member,capsule);
        postRepository.save(post);
        memberCapsule.write();
        memberCapsuleRepository.save(memberCapsule);
    }

    @Transactional
    public List<PostsResponseDto> readCapsulePost(Authentication authentication, long capsuleId){
        Member member = ((MemberDetails) authentication.getPrincipal()).getMember();
        MemberCapsule memberCapsule = memberCapsuleRepository.findByMemberAndCapsule_Id(member,capsuleId).orElseThrow( () ->
                new ErrorResponse(ExceptionMessage.CANNOT_READ));

        if(memberCapsule.getReadCheck().equals(Read.READ_DISABLE)){ // 읽을 수 없을때
            throw new ErrorResponse(ExceptionMessage.CANNOT_READ);
        }

        if(!memberCapsule.getCapsule().isReadAble()) // 읽을 수 없을 때
            throw new ErrorResponse(ExceptionMessage.CANNOT_READ);

        memberCapsule.readComplete(); // 읽기 완료
        memberCapsuleRepository.save(memberCapsule);

        List<Post> posts = postRepository.findAllByCapsule(memberCapsule.getCapsule());
        List<PostsResponseDto> results = new ArrayList<>();
        for(Post post : posts){
            PostsResponseDto responseDto = new PostsResponseDto(post.getMember().getName(),post.getImgUrl(),post.getMemo());
            results.add(responseDto);
        }
        return results;

    }
}
