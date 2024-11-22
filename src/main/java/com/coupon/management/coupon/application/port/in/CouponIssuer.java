package com.coupon.management.coupon.application.port.in;

import com.coupon.management.coupon.application.port.out.CouponRepository;
import com.coupon.management.coupon.domain.Coupon;
import com.coupon.management.event.application.port.out.EventRepository;
import com.coupon.management.event.domain.Event;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface CouponIssuer {
    Coupon issue(Long eventID, String memberID);

    @Service
    class CouponIssuerUseCase implements CouponIssuer {

        private final EventRepository eventRepository;
        private final CouponRepository couponRepository;

        public CouponIssuerUseCase(EventRepository eventRepository, CouponRepository couponRepository) {
            this.eventRepository = eventRepository;
            this.couponRepository = couponRepository;
        }
        
        @Override
        @Transactional
        public Coupon issue(Long eventID, String memberID) {
            Event event = eventRepository.findById(eventID);
            return couponRepository.save(new Coupon(event, memberID));
        }
    }
}