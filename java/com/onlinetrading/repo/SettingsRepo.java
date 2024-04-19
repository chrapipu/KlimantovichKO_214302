package com.onlinetrading.repo;

import com.onlinetrading.models.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepo extends JpaRepository<Settings, Long> {
}
