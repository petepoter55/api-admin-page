package com.apiadminpage.thread;

import com.apiadminpage.entity.product.Product;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.repository.product.ProductRepository;
import com.apiadminpage.service.line.LineNotificationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleProcessControlMethod {
    private static final Logger logger = Logger.getLogger(ScheduleProcessControlMethod.class);

    private final ProductRepository productRepository;
    private final LineNotificationService lineNotificationService;

    @Autowired
    public ScheduleProcessControlMethod(ProductRepository productRepository, LineNotificationService lineNotificationService) {
        this.productRepository = productRepository;
        this.lineNotificationService = lineNotificationService;
    }

    public ResponseEntity<String> taskQuantityProduct() {
        logger.info("Running ScheduleProcessRun taskQuantityProduct...");
        List<Product> product = new ArrayList<>();
        String productName = null;
        String result = null;
        int productCount = 0;

        try {
            product = productRepository.findAll();
            productName = product.stream().filter(e -> e.getProductQuantity().equals("0"))
                    .map(Product::getProductName).collect(Collectors.joining(","));
            productCount = (int) product.stream().filter(e -> e.getProductQuantity().equals("0")).count();

            if (!"".equals(productName)) {
                result = "รายการที่หมด มีทั้งหมด " + productCount + " รายการ คือ : " + productName;
            } else {
                result = "รายการที่หมด มีทั้งหมด " + productCount;
            }

        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            throw new ResponseException(Constant.STATUS_CODE_FAIL, e.getMessage());
        }

        logger.info("product name : " + productName);
        logger.info("product count : " + productCount);
        logger.info("Done ScheduleProcessRun taskQuantityProduct...");
        return this.lineNotificationService.httpPost(result);
    }
}
