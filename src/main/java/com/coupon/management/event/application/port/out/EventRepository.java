package com.coupon.management.event.application.port.out;

import com.coupon.management.event.domain.Event;
import com.coupon.management.event.domain.EventCoupon;
import com.coupon.management.event.infrastructure.out.repository.entity.EventEntity;
import com.coupon.management.event.infrastructure.out.repository.EventJpaRepository;
import com.coupon.management.event.infrastructure.out.repository.EventRedisRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

public interface EventRepository {
    Event findById(Long eventID);
    void initCount(Long eventID);
    int getIssuedCount(Long eventID);
    void increaseCount(Long eventID);

    @Repository
    class EventRepositoryImpl implements EventRepository {
        private final EventJpaRepository eventJpaRepository;
        private final EventRedisRepository eventRedisRepository;

        public EventRepositoryImpl(EventJpaRepository eventJpaRepository, EventRedisRepository eventRedisRepository) {
            this.eventJpaRepository = eventJpaRepository;
            this.eventRedisRepository = eventRedisRepository;
        }

        @Override
        public Event findById(Long eventID) {
            return eventJpaRepository.findById(eventID)
                    .map(EventEntity::toDomain)
                    .orElseThrow(() -> new NoSuchElementException("Event not found"));
        }
        @Override
        public void initCount(Long eventID) {
            eventRedisRepository.save(new EventCoupon(eventID, 0));
        }

        @Override
        public int getIssuedCount(Long eventID) {
            return eventRedisRepository.findByIdOrNull(eventID)
                    .map(EventCoupon::count)
                    .orElse(0);
        }

        @Override
        public void increaseCount(Long eventID) {
            eventRedisRepository.incrementCounts(eventID);
        }
    }
}