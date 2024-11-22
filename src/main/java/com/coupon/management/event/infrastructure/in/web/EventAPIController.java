package com.coupon.management.event.infrastructure.in.web;

import com.coupon.management.event.application.port.in.EventProcessor;
import com.coupon.management.event.infrastructure.in.web.request.IssueCouponRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventAPIController {
    private static final Logger logger = LoggerFactory.getLogger(EventAPIController.class);
    private final EventProcessor eventProcessor;

    public EventAPIController(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    @PostMapping("/issue-coupon")
    public void issueCoupon(@RequestBody IssueCouponRequest request) {
        logger.info("Coupon issued :: {} for {}", request.getEventID(), request.getMemberID());
        eventProcessor.process(request.getEventID(), request.getMemberID());
    }
}