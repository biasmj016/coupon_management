package com.coupon.management.coupon.application.port.in;

import com.coupon.management.coupon.application.port.out.CouponRepository;
import com.coupon.management.coupon.domain.Coupon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class CouponIssuerTest {
    @Autowired
    private CouponIssuer.CouponIssuerUseCase couponIssuerUseCase;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void save() {
        Long eventID = 1L;
        String memberID = "member1";

        Coupon coupon = couponIssuerUseCase.issue(eventID, memberID);
        assertNotNull(couponRepository.findByID(coupon.couponID()));
    }



}