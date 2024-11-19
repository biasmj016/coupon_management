package com.coupon.management.event.infrastructure.out.repository.entity;

import com.coupon.management.event.domain.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventID;

    private String name;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private int discountValue;

    private LocalDate startAt;

    private LocalDate endAt;

    private int maxIssuedCoupons;

    public static EventEntity toEventEntity(Event event) {
        return new EventEntity(
                event.eventID(),
                event.name(),
                event.eventType(),
                event.discountValue(),
                event.startAt(),
                event.endAt(),
                event.maxIssuedCoupons()
            );
    }

    public Event toDomain() {
        return new Event(
                this.eventID,
                this.name,
                this.eventType,
                this.discountValue,
                this.startAt,
                this.endAt,
                this.maxIssuedCoupons
        );
    }

    @Getter
    public enum EventType {
        FIXED("Fixed Amount Discount"),
        PERCENTAGE("Percentage Discount");

        private final String description;

        EventType(String description) {
            this.description = description;
        }

    }
}