package com.coupon.management.coupon.infrastructure.out.repository;

import com.coupon.management.coupon.infrastructure.out.repository.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {
    List<CouponEntity> findByMemberID(String memberID);

    @Query("SELECT COUNT(coupon) FROM CouponEntity coupon WHERE coupon.memberID = :memberID AND coupon.event.eventID = :eventID")
    int countByMemberIDAndEventID(@Param("memberID") String memberID, @Param("eventID") Long eventID);
}