package com.capsule.global.event;

import com.capsule.domain.member.model.Member;
import com.capsule.domain.capsule.model.Capsule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Event {
    private Member member;
    private Capsule capsule;
}
