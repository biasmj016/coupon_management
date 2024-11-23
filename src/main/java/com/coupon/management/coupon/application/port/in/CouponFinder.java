package com.coupon.management.coupon.application.port.in;

import com.coupon.management.coupon.application.port.out.CouponRepository;
import com.coupon.management.coupon.domain.Coupon;
import com.coupon.management.coupon.infrastructure.in.web.response.FindCouponResponse;
import com.coupon.management.coupon.infrastructure.in.web.response.FindCouponResponse.CouponDetails;
import com.coupon.management.event.domain.Event;
import org.springframework.stereotype.Service;

import java.util.List;

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
            List<CouponDetails> coupon = couponRepository.findByMemberID(memberID).stream().map(CouponDetails::new).toList();
            return new FindCouponResponse(coupon);
        }
    }
}