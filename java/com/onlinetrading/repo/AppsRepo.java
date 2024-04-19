package com.onlinetrading.repo;

import com.onlinetrading.models.Apps;
import com.onlinetrading.models.enums.AppStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppsRepo extends JpaRepository<Apps, Long> {
    List<Apps> findAllByStatus(AppStatus status);
}
