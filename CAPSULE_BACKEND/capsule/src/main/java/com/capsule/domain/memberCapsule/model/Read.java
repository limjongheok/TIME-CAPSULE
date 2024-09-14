package com.capsule.domain.memberCapsule.model;

import lombok.Getter;

@Getter
public enum Read {
    READ_DISABLE("ReadDisable"),
    READ_ABLE("ReadAble"),
    READ_COMPLETE("ReadComplete");

    private String readState;

    Read(String readState) {
        this.readState = readState;
    }
}
