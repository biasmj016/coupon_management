package com.coupon.management.event.infrastructure.in.web;

import com.coupon.management.event.application.port.out.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

@SpringBootTest
class EventAPIControllerTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void setUp() {
        eventRepository.initCount(1L);
        eventRepository.initCount(2L);
        eventRepository.initCount(3L);
    }

    @Test
    void test() throws InterruptedException {
        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:8080/issue-coupon")
                .build();

        var threadPool = Executors.newFixedThreadPool(32);
        var latch = new CountDownLatch(100);

        for (int i = 1; i <= 100; i++) {
            threadPool.submit(() -> {
                try {
                    client.post()
                            .retrieve()
                            .bodyToMono(String.class)
                            .log()
                            .subscribe();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        Thread.sleep(10000);
    }
}