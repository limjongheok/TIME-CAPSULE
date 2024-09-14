package com.capsule.domain.memberCapsule.repository;

import com.capsule.domain.member.model.Member;
import com.capsule.domain.memberCapsule.model.MemberCapsule;
import com.capsule.domain.memberCapsule.model.Read;
import com.capsule.domain.capsule.model.Capsule;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberCapsuleRepository extends JpaRepository<MemberCapsule,Long>{

    boolean existsByMemberAndReadCheck(Member member, Read read);
    boolean existsByMemberAndCapsule(Member member, Capsule capsule);
    MemberCapsule findByMemberAndCapsule(Member member , Capsule capsule);
    @EntityGraph(attributePaths = {"capsule"})
    Optional<MemberCapsule> findByMemberAndCapsule_Id(Member member , long capsuleId);

    @EntityGraph(attributePaths = {"member", "capsule"})
    List<MemberCapsule> findAllByMemberAndReadCheck(Member member, Read read);

    @EntityGraph(attributePaths = {"member"})
    List<MemberCapsule> findAllByCapsule(Capsule capsule);
}
