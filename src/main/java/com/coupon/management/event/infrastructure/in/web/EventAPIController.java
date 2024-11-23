package com.coupon.management.event.infrastructure.in.web;

import com.coupon.management.event.application.port.in.EventProcessor;
import com.coupon.management.event.application.port.in.EventValidator;
import com.coupon.management.event.infrastructure.in.web.request.IssueCouponRequest;
import com.coupon.management.global.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.coupon.management.global.response.ApiResponse.success;

@RestController
public class EventAPIController {
    private static final Logger logger = LoggerFactory.getLogger(EventAPIController.class);
    private final EventProcessor eventProcessor;
    private final EventValidator eventValidator;

    public EventAPIController(EventProcessor eventProcessor, EventValidator eventValidator) {
        this.eventProcessor = eventProcessor;
        this.eventValidator = eventValidator;
    }

    @PostMapping("/api/event/issue-coupon")
    public ApiResponse<Void> issueCoupon(@RequestBody IssueCouponRequest request) {
        logger.info("Coupon issued :: {} for {}", request.getEventID(), request.getMemberID());
        eventValidator.validate(request.getEventID(), request.getMemberID());
        eventProcessor.process(request.getEventID(), request.getMemberID());
        return success();
    }
}