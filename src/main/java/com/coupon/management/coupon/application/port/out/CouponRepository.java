package com.coupon.management.coupon.application.port.out;

import com.coupon.management.coupon.domain.Coupon;
import com.coupon.management.coupon.infrastructure.out.repository.CouponJpaRepository;
import com.coupon.management.coupon.infrastructure.out.repository.entity.CouponEntity;
import org.springframework.stereotype.Repository;

public interface CouponRepository {
    Coupon save(Coupon coupon);

    @Repository
    class CouponRepositoryImpl implements CouponRepository {
        private final CouponJpaRepository couponJpaRepository;

        public CouponRepositoryImpl(CouponJpaRepository couponJpaRepository) {
            this.couponJpaRepository = couponJpaRepository;
        }

        @Override
        public Coupon save(Coupon coupon) {
            CouponEntity couponEntity = new CouponEntity(coupon);
            return couponJpaRepository.save(couponEntity).toDomain();
        }
    }
}