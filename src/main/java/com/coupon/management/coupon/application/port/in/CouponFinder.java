package com.coupon.management.coupon.application.port.in;

import com.coupon.management.coupon.application.port.out.CouponRepository;
import com.coupon.management.coupon.domain.Coupon;
import org.springframework.stereotype.Service;

public interface CouponFinder {
    Coupon find(String memberID);

    @Service
    class CouponFinderUseCase implements CouponFinder {
        private final CouponRepository couponRepository;

        public CouponFinderUseCase(CouponRepository couponRepository) {
            this.couponRepository = couponRepository;
        }

        @Override
        public Coupon find(String memberID) {
            return couponRepository.findByMemberID(memberID);
        }
    }
}