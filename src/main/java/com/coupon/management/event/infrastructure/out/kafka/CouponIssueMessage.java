package com.coupon.management.event.infrastructure.out.kafka;

public record CouponIssueMessage(Long eventID, String memberID) {
}