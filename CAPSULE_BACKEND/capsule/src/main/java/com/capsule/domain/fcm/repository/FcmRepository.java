package com.capsule.domain.fcm.repository;

import com.capsule.domain.fcm.model.Fcm;
import com.capsule.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FcmRepository extends JpaRepository<Fcm,Long> {
    Optional<Fcm> findByMember(Member member);
    void deleteByMember(Member member);
    boolean existsByMember(Member member);
}
