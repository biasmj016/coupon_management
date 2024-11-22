package com.coupon.management.event.application.port.in;

import com.coupon.management.event.application.port.out.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"issue-coupon"})
@Testcontainers
class EventProcessorTest {

    @Autowired
    private EventProcessor.EventProcessorUseCase eventProcessor;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        eventRepository.initCount(1L);
    }

    @Test
    void process() {
        eventProcessor.process(1L, "member1");
        int issuedCount = eventRepository.getIssuedCount(1L);
        assertThat(issuedCount).isEqualTo(1);
    }

    @Test
    void process_exception() {
        for (int i = 0; i < 50; i++) {
            eventRepository.increaseCount(1L); // 100개의 쿠폰 발급
        }

        assertThatThrownBy(() -> eventProcessor.process(1L, "member1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("All coupons have been issued.");
    }
}
