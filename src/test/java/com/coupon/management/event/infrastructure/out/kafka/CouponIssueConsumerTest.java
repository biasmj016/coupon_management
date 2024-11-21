package com.coupon.management.event.infrastructure.out.kafka;

import com.coupon.management.coupon.application.port.in.CouponIssuer;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.Acknowledgment;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CouponIssueConsumerTest {

    @Test
    void handleIssueEvent() {
        CouponIssuer couponIssuer = mock(CouponIssuer.class);
        Acknowledgment acknowledgment = mock(Acknowledgment.class);
        CouponIssueConsumer consumer = new CouponIssueConsumer(couponIssuer);
        CouponIssueMessage message = new CouponIssueMessage(1L, "member1");

        consumer.handleIssueEvent(message, acknowledgment);

        verify(couponIssuer).issue(1L, "member1");
        verify(acknowledgment).acknowledge();
    }
}
