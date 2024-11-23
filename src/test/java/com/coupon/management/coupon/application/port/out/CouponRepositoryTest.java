package com.coupon.management.coupon.application.port.out;

import com.coupon.management.coupon.domain.Coupon;
import com.coupon.management.coupon.infrastructure.out.repository.CouponJpaRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponRepositoryTest {

    @Autowired
    private CouponJpaRepository couponJpaRepository;

    @Autowired
    private EventJpaRepository eventJpaRepository;

    @Container
    public static GenericContainer<?> postgreSQLContainer = new GenericContainer<>("postgres:13.3")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "mydb")
            .withEnv("POSTGRES_USER", "user")
            .withEnv("POSTGRES_PASSWORD", "password");

    private CouponRepository couponRepository;
    private Event event;

    @BeforeAll
    static void setUpContainer() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    void setUp() {
        couponRepository = new CouponRepository.CouponRepositoryImpl(couponJpaRepository);
        event = eventJpaRepository.findById(1L).orElseThrow().toDomain();
    }

    @Test
    void save() {
        Coupon savedCoupon = couponRepository.save(new Coupon(event, "member1"));
        assertThat(savedCoupon).isNotNull();

        Coupon coupon = couponRepository.findByID(savedCoupon.couponID());
        assertThat(savedCoupon.couponID()).isEqualTo(coupon.couponID());
    }

    @Test
    void findByMemberID() {
        couponRepository.save(new Coupon(event, "member2"));
        assertNotNull(couponRepository.findByMemberID("member2"));
    }

    @Test
    void useCoupon() {
        Coupon coupon = couponRepository.save(new Coupon(event, "member1"));

        couponRepository.useCoupon(coupon.couponID());

        Coupon updatedCoupon = couponRepository.findByID(coupon.couponID());
        assertNotNull(updatedCoupon.usedAt());
    }
}