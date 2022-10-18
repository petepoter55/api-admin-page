package com.apiadminpage.impl.model.product;

import com.apiadminpage.entity.product.Product;
import com.apiadminpage.model.request.product.ProductUpdateRequest;
import lombok.Data;

@Data
public class ProductUpdateTestRequest {
    private ProductUpdateRequest productUpdateRequest;
    private Product product;
}
