package com.coupon.management.coupon.application.port.in;

import com.coupon.management.coupon.application.port.out.CouponRepository;
import com.coupon.management.coupon.domain.Coupon;
import com.coupon.management.coupon.infrastructure.in.web.response.FindCouponResponse;
import com.coupon.management.event.domain.Event;
import org.springframework.stereotype.Service;

public interface CouponFinder {
    FindCouponResponse find(String memberID);

    @Service
    class CouponFinderUseCase implements CouponFinder {
        private final CouponRepository couponRepository;

        public CouponFinderUseCase(CouponRepository couponRepository) {
            this.couponRepository = couponRepository;
        }

        @Override
        public FindCouponResponse find(String memberID) {
            Coupon coupon = couponRepository.findByMemberID(memberID);
            Event event = coupon.event();
            return new FindCouponResponse(
                    coupon.couponID(),
                    coupon.memberID(),
                    event.name(),
                    event.eventType(),
                    event.discountValue(),
                    coupon.issuedAt(),
                    coupon.usedAt()
            );
        }
    }
}