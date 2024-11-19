package com.coupon.management.event.domain;

import com.coupon.management.event.infrastructure.out.repository.entity.EventEntity.EventType;

import java.time.LocalDate;

public record Event(
        Long eventID,
        String name,
        EventType eventType,
        int discountValue,
        LocalDate startAt,
        LocalDate endAt,
        int maxIssuedCoupons
) {}