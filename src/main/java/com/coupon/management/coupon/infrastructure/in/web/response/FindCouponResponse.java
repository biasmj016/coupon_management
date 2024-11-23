package com.coupon.management.coupon.infrastructure.in.web.response;

import com.coupon.management.coupon.domain.Coupon;
import com.coupon.management.event.domain.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FindCouponResponse {
    private List<CouponDetails> couponDetails;

    public FindCouponResponse(List<CouponDetails> couponDetails) {
        this.couponDetails = couponDetails;
    }

    @Getter
    @NoArgsConstructor
    public static class CouponDetails {
        private Long couponID;
        private String memberID;
        private String eventName;
        private String eventType;
        private int discountValue;
        private String issuedAt;
        private String usedAt;

        public CouponDetails(Coupon coupon) {
            Event event = coupon.event();
            this.couponID = coupon.couponID();
            this.memberID = coupon.memberID();
            this.eventName = event.name();
            this.eventType = event.formatEventType();
            this.discountValue = event.discountValue();
            this.issuedAt = coupon.formatIssuedAt();
            this.usedAt = coupon.formatUsedAt();
        }
    }
}
