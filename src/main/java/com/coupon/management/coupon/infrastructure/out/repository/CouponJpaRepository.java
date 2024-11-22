package com.coupon.management.coupon.infrastructure.out.repository;

import com.coupon.management.coupon.infrastructure.out.repository.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {
    Optional<CouponEntity> findByMemberID(String memberID);
}