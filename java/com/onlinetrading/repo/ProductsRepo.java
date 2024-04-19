package com.onlinetrading.repo;

import com.onlinetrading.models.Products;
import com.onlinetrading.models.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepo extends JpaRepository<Products, Long> {

    List<Products> findAllByNameContainingAndStatus(String name,ProductStatus status);
    List<Products> findAllByStatus(ProductStatus status);
}
