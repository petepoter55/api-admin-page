package com.apiadminpage.impl.service;

import com.apiadminpage.entity.product.Product;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.impl.model.product.ProductSearchTestRequest;
import com.apiadminpage.model.response.Response;
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

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductSearchTest {
    private static final Logger logger = Logger.getLogger(ProductSearchTest.class);

    @Mock
    private EntityManager entityManager;
    @Mock
    private CriteriaBuilder criteriaBuilder;
    @Mock
    private CriteriaQuery<Product> criteriaQuery;
    @Mock
    private Root<Product> root;
    @Mock
    private TypedQuery typedQuery;
    @InjectMocks
    private ProductService productService;

    @Test
    public void searchProductByPage() throws Exception {
        File[] files = readTestCase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setDateFormat(simpleDateFormat);

        try {
            for (File file : files) {
                // ARRANGE
                ProductSearchTestRequest productSearchTestRequest = mapper.readValue(file, ProductSearchTestRequest.class);
                setup(productSearchTestRequest);

                //ACT
                Response responseActual = productService.searchProductByPage(productSearchTestRequest.getProductInquiryRequest());
                List<Product> actual = (List<Product>) responseActual.getData();

                // ASSERT
                List<Product> expected = mapper.readValue(FileUtils.readFileToString(new File(FilenameUtils.concat("src/test/resources/case-function/searchProduct/expected", file.getName())), StandardCharsets.UTF_8), new TypeReference<List<Product>>() {
                });
                logger.info("actual : " + actual);
                logger.info("expected : " + expected);

                for (int i = 0; i < actual.size(); i++) {
                    assertEquals(expected.get(i).getProductName(), actual.get(i).getProductName());
                    assertEquals(expected.get(i).getProductCode(), actual.get(i).getProductCode());
                    assertEquals(expected.get(i).getProductType(), actual.get(i).getProductType());
                }
            }
        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
        }
    }

    private File[] readTestCase() throws Exception {
        File folder = new File("src/test/resources/case-function/searchProduct/request");
        return folder.listFiles();
    }

    private void setup(ProductSearchTestRequest request) {
        List<Product> productList = new ArrayList<>();
        if (request.getProducts() != null) {
            productList.addAll(request.getProducts());
        }

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Product.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Product.class)).thenReturn(root);
        // when create query then return typeQuery
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.setFirstResult(0)).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(2)).thenReturn(typedQuery);
        // get ResultList by data mock
        when(typedQuery.getResultList()).thenReturn(productList);
    }
}