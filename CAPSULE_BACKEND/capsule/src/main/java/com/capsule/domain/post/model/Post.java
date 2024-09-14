package com.capsule.domain.post.model;

import com.capsule.domain.capsule.model.Capsule;
import com.capsule.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long id;

    @Column(name = "img_url")
    private String imgUrl;

    @Lob
    @Column(name = "memo", columnDefinition = "TEXT")
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capsule_id")
    private Capsule capsule;

    @Builder
    private Post(String imgUrl, String memo, Member member, Capsule capsule) {
        this.imgUrl = imgUrl;
        this.memo = memo;
        this.member = member;
        this.capsule = capsule;
    }

    public static Post createPost(String imgUrl, String memo, Member member, Capsule capsule){
        return Post.builder()
                .imgUrl(imgUrl)
                .memo(memo)
                .member(member)
                .capsule(capsule)
                .build();
    }
}
