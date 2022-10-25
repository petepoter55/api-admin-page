package com.apiadminpage.thread;

import com.apiadminpage.entity.product.Product;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.repository.product.ProductRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleProcessControlMethod {
    private static final Logger logger = Logger.getLogger(ScheduleProcessControlMethod.class);
    private final URI lineUrl = URI.create(Constant.URL_NOTIFICATION);
    private final String contentType = Constant.CONTENT_TYPE_REQUEST_LINE;

    @Value("${line-token}")
    private String LINE_TOKEN;

    private final ProductRepository productRepository;

    @Autowired
    public ScheduleProcessControlMethod(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

            if (productName != null && !("").equals(productName)){
                result = "รายการที่หมด มีทั้งหมด " + productCount + " รายการ คือ : " + productName;
            }else {
                result = "รายการที่หมด มีทั้งหมด " + productCount ;
            }

        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            throw new ResponseException(Constant.STATUS_CODE_FAIL, e.getMessage());
        }

        logger.info("product name : " + productName);
        logger.info("product count : " + productCount);
        logger.info("Done ScheduleProcessRun taskQuantityProduct...");
        return this.httpPost(result);
    }

    public ResponseEntity<String> httpPost(String data) {
        String bearerAuth = "Bearer " + LINE_TOKEN;
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", contentType);
        headers.add("Content-Length", "" + data.getBytes().length);
        headers.add("Authorization", bearerAuth);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("message", data);

        RequestEntity request = new RequestEntity(body, headers, HttpMethod.POST, lineUrl);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(request, String.class);
    }

}
