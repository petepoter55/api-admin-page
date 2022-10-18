package com.apiadminpage.impl.service;

import com.apiadminpage.entity.product.Product;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.impl.model.product.ProductUpdateTestRequest;
import com.apiadminpage.model.response.Response;
import com.apiadminpage.repository.product.ProductRepository;
import com.apiadminpage.service.product.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;



import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductUpdateTest {
    private static final Logger logger = Logger.getLogger(ProductUpdateTest.class);

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    public void updateProductTest() throws Exception {
        File[] files = readTestCase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setDateFormat(simpleDateFormat);
        try {
            for (File file : files) {
                // ARRANGE
                ProductUpdateTestRequest productUpdateTestRequest = mapper.readValue(file, ProductUpdateTestRequest.class);
                setup(productUpdateTestRequest);

                //ACT
                Response responseActual = productService.updateProduct(productUpdateTestRequest.getProductUpdateRequest());
                Product actual = (Product) responseActual.getData();

                // ASSERT
                Product expected = mapper.readValue(FileUtils.readFileToString(new File(FilenameUtils.concat("src/test/resources/case-function/updateProduct/expected", file.getName())), StandardCharsets.UTF_8), new TypeReference<Product>() {
                });

                logger.info("actual : " + actual);
                logger.info("expected : " + expected);

                assertEquals(expected.getProductName(), actual.getProductName());
                assertEquals(expected.getProductCode(), actual.getProductCode());
                assertEquals(expected.getProductType(), actual.getProductType());

            }
        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
        }
    }

    private File[] readTestCase() throws Exception {
        File folder = new File("src/test/resources/case-function/updateProduct/request");
        return folder.listFiles();
    }

    private void setup(ProductUpdateTestRequest request) {
        when(productRepository.findByProductCode(any())).thenReturn(request.getProduct());
    }
}
