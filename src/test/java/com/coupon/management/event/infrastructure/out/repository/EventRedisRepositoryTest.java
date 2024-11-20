package com.coupon.management.event.infrastructure.out.repository;

import com.coupon.management.event.domain.EventCoupon;
import com.coupon.management.global.config.TestRedisConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringJUnitConfig(classes = {TestRedisConfig.class, EventRedisRepository.class})
class EventRedisRepositoryTest {

    @Container
    public static GenericContainer<?> redisContainer = new GenericContainer<>("redis:6.2.6")
            .withExposedPorts(6379);

    @Autowired
    private EventRedisRepository eventRedisRepository;

    @BeforeEach
    void setUp() {
        redisContainer.start();
    }

    @Test
    void findByIdOrNull() {
        EventCoupon coupon = new EventCoupon(1L, 5);
        eventRedisRepository.save(coupon);

        var result = eventRedisRepository.findByIdOrNull(1L);

        System.out.println("result :: "+result);

        assertThat(result).isPresent();
        assertThat(result.get().count()).isEqualTo(5);
    }

    @Test
    void incrementCounts() {
        EventCoupon coupon = new EventCoupon(2L, 10);
        eventRedisRepository.save(coupon);

        eventRedisRepository.incrementCounts(2L);

        var result = eventRedisRepository.findByIdOrNull(2L);

        System.out.println("result :: "+result);

        assertThat(result).isPresent();
        assertThat(result.get().count()).isEqualTo(11);
    }

}