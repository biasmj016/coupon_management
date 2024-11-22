package com.coupon.management.event.application.port.in;

import com.coupon.management.event.application.port.out.EventRepository;
import com.coupon.management.event.domain.Event;
import com.coupon.management.event.infrastructure.out.kafka.CouponIssueProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public interface EventProcessor {
    void process(Long eventID, String memberID);

    @Service
    class EventProcessorUseCase implements EventProcessor {
        private static final Logger logger = LoggerFactory.getLogger(EventProcessor.class);
        private final EventRepository eventRepository;
        private final CouponIssueProducer producer;

        public EventProcessorUseCase(EventRepository eventRepository, CouponIssueProducer producer) {
            this.eventRepository = eventRepository;
            this.producer = producer;
        }

        @Override
        public void process(Long eventID, String memberID) {
            validate(eventID);
            eventRepository.increaseCount(eventID);
            producer.sendIssueEvent(eventID, memberID);
        }

        private void validate(Long eventID) {
            int issuedCount = eventRepository.getIssuedCount(eventID);
            Event event = eventRepository.findById(eventID);
            logger.info("Issued count: {}, event max Issued Coupons : {}", issuedCount, event.maxIssuedCoupons());
            if (!event.isIssuable(issuedCount)) {
                logger.error("All coupons have been issued. Issuance of coupons for this event ({}) is now discontinued.", eventID);
                throw new IllegalArgumentException("All coupons have been issued.");
            }
        }
    }
}