package com.capsule.domain.memberCapsule.message.responseDto;

import com.capsule.domain.memberCapsule.model.Read;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReadCompleteResponseDto {
    long capsuleId;
    private String latitude;
    private String longitude;

}
