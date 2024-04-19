package com.onlinetrading.repo;

import com.onlinetrading.models.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepo extends JpaRepository<Reviews, Long> {
}
