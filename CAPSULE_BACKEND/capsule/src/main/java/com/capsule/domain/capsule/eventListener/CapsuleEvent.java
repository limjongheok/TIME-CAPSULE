package com.capsule.domain.capsule.eventListener;

import com.capsule.domain.capsule.model.Capsule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CapsuleEvent {

    private List<Capsule> rooms;
}
