package com.coupon.management.event.infrastructure.out.repository;

import com.coupon.management.event.domain.EventCoupon;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EventRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY = "coupon";

    public EventRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(EventCoupon coupon) {
        redisTemplate.opsForHash().put(KEY, coupon.eventID(), coupon.count());
    }

    public Optional<EventCoupon> findByIdOrNull(Long eventID) {
        return Optional.ofNullable((String) redisTemplate.opsForHash().get(KEY, eventID))
                .map(count -> new EventCoupon(eventID, Integer.valueOf(count)));
    }

    public void incrementCounts(Long eventID) {
        redisTemplate.opsForHash().increment(KEY, eventID, 1);
    }
}