package com.capsule.domain.AI.AIImgRepository;

import com.capsule.domain.AI.model.AIImg;
import com.capsule.domain.capsule.model.Capsule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIImgRepository extends JpaRepository<AIImg, Long> {

    boolean existsByCapsule(Capsule capsule);

    AIImg findByCapsuleId(long capsuleId);
}
