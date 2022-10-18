package com.apiadminpage.impl.model.product;

import com.apiadminpage.entity.product.Product;
import com.apiadminpage.model.request.product.ProductInquiryRequest;
import lombok.Data;

import java.util.List;

@Data
public class ProductSearchTestRequest {
    private ProductInquiryRequest productInquiryRequest;
    private List<Product> products;
}
