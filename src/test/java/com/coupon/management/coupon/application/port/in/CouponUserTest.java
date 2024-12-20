package com.coupon.management.coupon.application.port.in;

import com.coupon.management.coupon.application.port.out.CouponRepository;
import com.coupon.management.coupon.domain.Coupon;
import com.coupon.management.coupon.infrastructure.out.repository.CouponJpaRepository;
import com.coupon.management.event.domain.Event;
import com.coupon.management.event.infrastructure.out.repository.EventJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CouponUserTest {
    @Autowired
    private CouponUser.CouponUserUseCase couponUserUseCase;

    @Autowired
    private CouponJpaRepository couponJpaRepository;

    @Autowired
    private EventJpaRepository eventJpaRepository;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        CouponRepository couponRepository = new CouponRepository.CouponRepositoryImpl(couponJpaRepository);
        Event event = eventJpaRepository.findById(1L).orElseThrow().toDomain();
        coupon = couponRepository.save(new Coupon(event, "coupon_user_test"));
    }

    @Test
    void use() {
        couponUserUseCase.use(coupon.couponID());
        Coupon usedCoupon = couponJpaRepository.findById(coupon.couponID()).orElseThrow().toDomain();
        assertNotNull(usedCoupon.usedAt());
    }
}