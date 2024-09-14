package com.capsule.domain.capsule.repository;

import com.capsule.domain.capsule.model.Capsule;

import java.util.List;

public interface CapsuleRepositoryCustom {

     List<Capsule> findByMinuteEqualsToNowEndTime();
}
