package com.capsule.domain.capsule.repository;

import com.capsule.domain.capsule.model.Capsule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapsuleRepository extends JpaRepository<Capsule,Long>, CapsuleRepositoryCustom {

    @Override
    List<Capsule> findByMinuteEqualsToNowEndTime();
}
