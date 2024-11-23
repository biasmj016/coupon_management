package com.coupon.management.event.application.port.in;

import com.coupon.management.event.application.port.out.EventRepository;
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
            logger.info("Issuing coupon for eventID: {} and memberID: {}", eventID, memberID);
            eventRepository.increaseCount(eventID);
            producer.sendIssueEvent(eventID, memberID);
        }
    }
}