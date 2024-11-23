package com.coupon.management.coupon.infrastructure.out.repository;

import com.coupon.management.coupon.domain.Coupon;
import com.coupon.management.coupon.infrastructure.out.repository.entity.CouponEntity;
import com.coupon.management.event.domain.Event;
import com.coupon.management.event.infrastructure.out.repository.EventJpaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponJpaRepositoryTest {

    @Autowired
    private CouponJpaRepository couponJpaRepository;

    @Autowired
    private EventJpaRepository eventJpaRepository;

    private CouponEntity couponEntity;
    private Event event;

    @Container
    public static GenericContainer<?> postgreSQLContainer = new GenericContainer<>("postgres:13.3")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "mydb")
            .withEnv("POSTGRES_USER", "user")
            .withEnv("POSTGRES_PASSWORD", "password");


    @BeforeAll
    static void setUpContainer() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    void setUp() {
        event = eventJpaRepository.findById(1L).orElseThrow().toDomain();
        couponEntity = new CouponEntity(new Coupon(event, "coupon_jpa_test"));
        couponJpaRepository.save(couponEntity);
    }

    @Test
    void findByID() {
        assertNotNull(couponJpaRepository.findById(couponEntity.getCouponID()));
    }

    @Test
    void countByMemberIDAndEventID() {
        int count = couponJpaRepository.countByMemberIDAndEventID(couponEntity.getMemberID(), event.eventID());
        assertEquals(1, count);
    }
}