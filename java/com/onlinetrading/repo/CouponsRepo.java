package com.onlinetrading.repo;

import com.onlinetrading.models.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponsRepo extends JpaRepository<Coupons, Long> {
}
