package com.coupon.management.event.infrastructure.out.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CouponIssueProducer {
    private static final Logger logger = LoggerFactory.getLogger(CouponIssueProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CouponIssueProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendIssueEvent(Long eventID, String memberID) {
        logger.info("Producer sending issue event for eventID: {} and memberID: {}", eventID, memberID);
        kafkaTemplate.send("issue-coupon", new CouponIssueMessage(eventID, memberID));
    }
}