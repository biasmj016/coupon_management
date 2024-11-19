package com.coupon.management.coupon.infrastructure.out.repository.jpa;

import com.coupon.management.coupon.infrastructure.out.repository.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {
}