package com.coupon.management.event.infrastructure.out.kafka;

import com.coupon.management.coupon.application.port.in.CouponIssuer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class CouponIssueConsumer {

    private final CouponIssuer couponIssuer;

    public CouponIssueConsumer(CouponIssuer couponIssuer) {
        this.couponIssuer = couponIssuer;
    }

    @KafkaListener(topics = "issue-coupon", groupId = "coupon")
    public void handleIssueEvent(CouponIssueMessage message, Acknowledgment ack) {
        couponIssuer.issue(message.eventID(), message.memberID());
        ack.acknowledge();
    }
}