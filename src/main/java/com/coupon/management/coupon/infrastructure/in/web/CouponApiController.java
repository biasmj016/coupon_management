package com.coupon.management.coupon.infrastructure.in.web;

import com.coupon.management.coupon.application.port.in.CouponFinder;
import com.coupon.management.coupon.application.port.in.CouponUser;
import com.coupon.management.coupon.infrastructure.in.web.response.FindCouponResponse;
import com.coupon.management.global.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import static com.coupon.management.global.response.ApiResponse.success;

@RestController
public class CouponApiController {
    private final CouponFinder couponFinder;
    private final CouponUser couponUser;

    public CouponApiController(CouponFinder couponFinder, CouponUser couponUser) {
        this.couponFinder = couponFinder;
        this.couponUser = couponUser;
    }

    @GetMapping("/api/coupon")
    public ApiResponse<FindCouponResponse> find(@RequestParam String memberID) {
        return success(couponFinder.find(memberID));
    }

    @PostMapping("/api/coupon/{couponID}/use")
    public ApiResponse<Void> find(@PathVariable Long couponID) {
        couponUser.use(couponID);
        return success();
    }
}
