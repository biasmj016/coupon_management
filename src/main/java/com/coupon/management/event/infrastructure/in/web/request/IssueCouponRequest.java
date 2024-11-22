package com.coupon.management.event.infrastructure.in.web.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class IssueCouponRequest {
    private Long eventID;
    private String memberID;

    public IssueCouponRequest(Long eventID, String memberID) {
        this.eventID = eventID;
        this.memberID = memberID;
    }
}
