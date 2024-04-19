package com.onlinetrading.repo;

import com.onlinetrading.models.Incomes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomesRepo extends JpaRepository<Incomes, Long> {
}
