package com.coupon.management.coupon.domain;

import com.coupon.management.event.domain.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Coupon(
        Long couponID,
        Event event,
        String memberID,
        LocalDate issuedAt,
        LocalDateTime usedAt
) {
    public Coupon(Event event, String memberID) {
        this(null, event, memberID, LocalDate.now(), null);
    }
}