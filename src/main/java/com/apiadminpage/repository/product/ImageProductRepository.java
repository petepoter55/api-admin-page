package com.apiadminpage.repository.product;

import com.apiadminpage.entity.product.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageProductRepository extends JpaRepository<ImageProduct, Integer> {
    ImageProduct findByProductCode(String productCode);
}
