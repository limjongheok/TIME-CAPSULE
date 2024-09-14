package com.capsule.domain.capsule.repository;

import com.capsule.domain.capsule.model.Capsule;


import com.capsule.domain.capsule.model.QCapsule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;

public class CapsuleRepositoryImpl implements CapsuleRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QCapsule capsule = QCapsule.capsule;

    public CapsuleRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Capsule> findByMinuteEqualsToNowEndTime() {
        LocalDateTime now = LocalDateTime.now();

        return queryFactory.selectFrom(capsule)
                .where(capsule.endTime.year().eq(now.getYear())
                        .and(capsule.endTime.month().eq(now.getMonthValue()))
                        .and(capsule.endTime.dayOfMonth().eq(now.getDayOfMonth()))
                        .and(capsule.endTime.hour().eq(now.getHour()))
                        .and(capsule.endTime.minute().eq(now.getMinute())))
                .fetch();
    }
}
