package com.coupon.management.event.infrastructure.out.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CouponIssueProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CouponIssueProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendIssueEvent(Long eventID, String memberID) {
        kafkaTemplate.send("issue-coupon", new CouponIssueMessage(eventID, memberID));
    }
}