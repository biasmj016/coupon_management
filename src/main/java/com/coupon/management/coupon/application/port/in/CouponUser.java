package com.coupon.management.coupon.application.port.in;

import com.coupon.management.coupon.application.port.out.CouponRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

public interface CouponUser {
    void use(Long couponID);

    @Service
    class CouponUserUseCase implements CouponUser {
        private final CouponRepository couponRepository;

        public CouponUserUseCase(CouponRepository couponRepository) {
            this.couponRepository = couponRepository;
        }

        @Override
        @Transactional
        public void use(Long couponID) {
            couponRepository.useCoupon(couponID);
        }
    }
}