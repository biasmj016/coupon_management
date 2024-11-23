package com.coupon.management.event.application.port.in;

import com.coupon.management.coupon.application.port.out.CouponRepository;
import com.coupon.management.event.application.port.out.EventRepository;
import com.coupon.management.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public interface EventValidator {
    void validate(Long eventID, String memberID);

    @Service
    class EventValidatorUseCase implements EventValidator {
        private static final Logger logger = LoggerFactory.getLogger(EventValidator.class);
        private final CouponRepository couponRepository;
        private final EventRepository eventRepository;

        public EventValidatorUseCase(CouponRepository couponRepository, EventRepository eventRepository) {
            this.couponRepository = couponRepository;
            this.eventRepository = eventRepository;
        }

        @Override
        public void validate(Long eventID, String memberID) {
            logger.info("Validating event {} for member {}", eventID, memberID);
            if(!checkIssuable(eventID)){
                logger.error("All coupons have been issued. Issuance of coupons for this event ({}) is now discontinued.", eventID);
                throw new IllegalArgumentException("All coupons have been issued.");
            }

            if(checkDuplicateIssue(eventID, memberID)){
                logger.error("Coupon already issued for member {} and event {}", memberID, eventID);
                throw new IllegalArgumentException("Coupon already issued");
            }
        }

        private boolean checkIssuable(Long eventID) {
            int issuedCount = eventRepository.getIssuedCount(eventID);
            Event event = eventRepository.findById(eventID);
            logger.info("Issued count: {}, event max Issued Coupons : {}", issuedCount, event.maxIssuedCoupons());
            return event.isIssuable(issuedCount);
        }

        private boolean checkDuplicateIssue(Long eventID, String memberID) {
            return couponRepository.findEverIssued(memberID, eventID);
        }
    }
}