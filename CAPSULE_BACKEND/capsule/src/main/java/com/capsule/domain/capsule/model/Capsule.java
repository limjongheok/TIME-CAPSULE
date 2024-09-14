package com.capsule.domain.capsule.model;

import com.capsule.global.baseTimeEntity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Capsule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "capsule_id")
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "end_time",nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @Column(name = "latitude", nullable = false)
    private String latitude;

    @Column(name = "longitude", nullable = false)
    private String longitude;

    @Column(name = "read_able", nullable = false)
    private boolean readAble;

    @Column(name = "write_able" , nullable = false)
    private boolean writeAble;

    @Builder
    private Capsule(String title, LocalDateTime endTime, String latitude, String longitude, boolean readAble, boolean writeAble) {
        this.title = title;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.readAble = readAble;
        this.writeAble = writeAble;
    }

    public static Capsule createCapsule(String title, LocalDateTime endTime, String latitude, String longitude, boolean readAble, boolean writeAble){
        return Capsule.builder()
                .title(title)
                .endTime(endTime)
                .latitude(latitude)
                .longitude(longitude)
                .readAble(readAble)
                .writeAble(writeAble)
                .build();
    }

    public void readAbleCapsule(){
        this.readAble = true;
    }

    public void writeDisableCapsule(){
        this.writeAble = false;
    }
}
