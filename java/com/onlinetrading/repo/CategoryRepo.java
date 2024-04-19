package com.onlinetrading.repo;

import com.onlinetrading.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
