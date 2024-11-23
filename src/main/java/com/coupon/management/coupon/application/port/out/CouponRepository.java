package com.coupon.management.coupon.application.port.out;

import com.coupon.management.coupon.domain.Coupon;
import com.coupon.management.coupon.infrastructure.out.repository.CouponJpaRepository;
import com.coupon.management.coupon.infrastructure.out.repository.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

public interface CouponRepository {
    Coupon save(Coupon coupon);
    Coupon findByID(Long couponID);
    Coupon findByMemberID(String memberID);
    boolean findEverIssued(String memberID, Long eventID);
    void useCoupon(Long couponID);

    @Repository
    class CouponRepositoryImpl implements CouponRepository {
        private final CouponJpaRepository couponJpaRepository;

        public CouponRepositoryImpl(CouponJpaRepository couponJpaRepository) {
            this.couponJpaRepository = couponJpaRepository;
        }

        @Override
        public Coupon save(Coupon coupon) {
            CouponEntity couponEntity = new CouponEntity(coupon);
            return couponJpaRepository.save(couponEntity).toDomain();
        }

        @Override
        public Coupon findByID(Long couponID) {
            return couponJpaRepository.findById(couponID)
                    .map(CouponEntity::toDomain)
                    .orElseThrow(() -> new NoSuchElementException("Coupon not found"));
        }

        @Override
        public Coupon findByMemberID(String memberID) {
            return couponJpaRepository.findByMemberID(memberID)
                    .map(CouponEntity::toDomain)
                    .orElseThrow(() -> new NoSuchElementException("Coupon not found"));
        }

        @Override
        public boolean findEverIssued(String memberID, Long eventID) {
            return couponJpaRepository.countByMemberIDAndEventID(memberID, eventID) > 0;
        }

        @Override
        public void useCoupon(Long couponID) {
            couponJpaRepository.findById(couponID)
                    .map(CouponEntity::useCoupon)
                    .orElseThrow(() -> new NoSuchElementException("Coupon not found"));
        }
    }
}