package com.apiadminpage.repository.product;

import com.apiadminpage.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductCode(String productCode);
    Product findByProductName(String productName);
}
