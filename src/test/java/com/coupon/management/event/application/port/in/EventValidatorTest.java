package com.coupon.management.event.application.port.in;

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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class EventValidatorTest {
    @Autowired
    private EventValidator.EventValidatorUseCase eventValidator;

    @Autowired
    private CouponJpaRepository couponJpaRepository;

    @Autowired
    private EventJpaRepository eventJpaRepository;

    @BeforeEach
    void setUp() {
        CouponRepository couponRepository = new CouponRepository.CouponRepositoryImpl(couponJpaRepository);
        Event event = eventJpaRepository.findById(1L).orElseThrow().toDomain();
        couponRepository.save(new Coupon(event, "coupon_validator_test"));
    }

    @Test
    void validate_exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventValidator.validate(1L, "coupon_validator_test");
        });
    }

    @Test
    void validate() {
        assertDoesNotThrow(() -> eventValidator.validate(1L, "valid_member"));
    }
}