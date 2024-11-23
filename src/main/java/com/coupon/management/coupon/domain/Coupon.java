package com.coupon.management.coupon.domain;

import com.coupon.management.event.domain.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public String formatIssuedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return issuedAt == null ? "not issued" : formatter.format(issuedAt);
    }

    public String formatUsedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return usedAt == null ? "not used" : formatter.format(usedAt);
    }

}