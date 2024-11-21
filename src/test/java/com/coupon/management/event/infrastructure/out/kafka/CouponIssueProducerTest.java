package com.coupon.management.event.infrastructure.out.kafka;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CouponIssueProducerTest {

    @Test
    void sendIssueEvent() {
        KafkaTemplate<String, Object> kafkaTemplate = mock(KafkaTemplate.class);
        CouponIssueProducer producer = new CouponIssueProducer(kafkaTemplate);
        Long eventID = 1L;
        String memberID = "member1";

        producer.sendIssueEvent(eventID, memberID);

        verify(kafkaTemplate).send(eq("issue-coupon"), eq(new CouponIssueMessage(eventID, memberID)));
    }
}