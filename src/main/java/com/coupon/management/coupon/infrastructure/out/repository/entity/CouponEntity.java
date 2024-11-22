package com.coupon.management.coupon.infrastructure.out.repository.entity;

import com.coupon.management.coupon.domain.Coupon;
import com.coupon.management.event.infrastructure.out.repository.entity.EventEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.coupon.management.event.infrastructure.out.repository.entity.EventEntity.toEventEntity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coupon")
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponID;

    @ManyToOne
    @JoinColumn(name = "eventID", nullable = false)
    private EventEntity event;

    private String memberID;

    private Boolean isUsed = false;

    private LocalDate issuedAt;

    private LocalDateTime usedAt;


    public CouponEntity(Coupon coupon) {
        this.event = toEventEntity(coupon.event());
        this.memberID = coupon.memberID();
        this.issuedAt = coupon.issuedAt();
        this.usedAt = coupon.usedAt();
    }

    public Coupon toDomain() {
        return new Coupon(
                this.couponID,
                this.event.toDomain(),
                this.memberID,
                this.issuedAt,
                this.usedAt
        );
    }

    public CouponEntity useCoupon() {
        this.isUsed = true;
        this.usedAt = LocalDateTime.now();
        return this;
    }
}
