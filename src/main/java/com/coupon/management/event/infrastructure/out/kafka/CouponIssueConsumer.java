package com.coupon.management.event.infrastructure.out.kafka;

import com.coupon.management.coupon.application.port.in.CouponIssuer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class CouponIssueConsumer {
    private static final Logger logger = LoggerFactory.getLogger(CouponIssueConsumer.class);
    private final CouponIssuer couponIssuer;

    public CouponIssueConsumer(CouponIssuer couponIssuer) {
        this.couponIssuer = couponIssuer;
    }

    @KafkaListener(topics = "issue-coupon", groupId = "coupon")
    public void handleIssueEvent(CouponIssueMessage message, Acknowledgment ack) {
        logger.info("Consumer receiving issue event for eventID: {} and memberID : {}", message.eventID(), message.memberID());
        couponIssuer.issue(message.eventID(), message.memberID());
        ack.acknowledge();
    }
}