package com.onlinetrading.repo;

import com.onlinetrading.models.Cumulatives;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CumulativesRepo extends JpaRepository<Cumulatives, Long> {
    List<Cumulatives> findAllByOrderByMin();
}
