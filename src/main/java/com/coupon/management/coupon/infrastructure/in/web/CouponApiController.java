package com.coupon.management.coupon.infrastructure.in.web;

import com.coupon.management.coupon.application.port.in.CouponFinder;
import com.coupon.management.coupon.application.port.in.CouponUser;
import com.coupon.management.coupon.infrastructure.in.web.response.FindCouponResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class CouponApiController {
    private final CouponFinder couponFinder;
    private final CouponUser couponUser;

    public CouponApiController(CouponFinder couponFinder, CouponUser couponUser) {
        this.couponFinder = couponFinder;
        this.couponUser = couponUser;
    }

    @GetMapping("/api/coupon")
    public FindCouponResponse find(@RequestParam String memberID) {
        return couponFinder.find(memberID);
    }

    @PostMapping("/api/coupon/{couponID}/use")
    public void find(@PathVariable Long couponID) {
        couponUser.use(couponID);
    }
}
