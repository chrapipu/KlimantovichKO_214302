package com.onlinetrading.repo;

import com.onlinetrading.models.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartsRepo extends JpaRepository<Carts, Long> {
}
