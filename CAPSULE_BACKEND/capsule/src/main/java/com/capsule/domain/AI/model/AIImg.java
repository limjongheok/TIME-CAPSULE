package com.capsule.domain.AI.model;

import com.capsule.domain.capsule.model.Capsule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AIImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aiImg_id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capsule_id")
    private Capsule capsule;

    @Lob
    @Column(name = "img_url", columnDefinition = "TEXT")
    private String imgUrl;

    public AIImg(Capsule capsule, String imgUrl) {
        this.capsule = capsule;
        this.imgUrl = imgUrl;
    }
}
