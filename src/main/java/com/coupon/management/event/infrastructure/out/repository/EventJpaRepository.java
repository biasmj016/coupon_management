package com.coupon.management.event.infrastructure.out.repository;

import com.coupon.management.event.infrastructure.out.repository.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {
}