package com.coupon.management.coupon.infrastructure.in.web.response;

import com.coupon.management.event.infrastructure.out.repository.entity.EventEntity.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class FindCouponResponse {
    private Long couponID;
    private String memberID;
    private String eventName;
    private String eventType;
    private int discountValue;
    private String issuedAt;
    private String usedAt;

    public FindCouponResponse(Long couponID, String memberID, String eventName, EventType eventType, int discountValue, LocalDate issuedAt, LocalDateTime usedAt) {
        this.couponID = couponID;
        this.memberID = memberID;
        this.eventName = eventName;
        this.eventType = eventType.getDescription();
        this.discountValue = discountValue;
        this.issuedAt = convertLocalDate(issuedAt);
        this.usedAt = convertLocalDateTime(usedAt);
    }

    private String convertLocalDate(LocalDate date) {
        if(date == null) return "none issued";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(date);
    }

    private String convertLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "none used";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return formatter.format(dateTime);
    }
}
