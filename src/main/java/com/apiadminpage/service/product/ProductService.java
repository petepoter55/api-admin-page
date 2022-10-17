package com.apiadminpage.service.product;

import com.apiadminpage.entity.product.Product;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.request.product.ProductInquiryRequest;
import com.apiadminpage.model.request.product.ProductRequest;
import com.apiadminpage.model.request.product.ProductUpdateRequest;
import com.apiadminpage.model.response.Response;
import com.apiadminpage.repository.product.ProductRepository;
import com.apiadminpage.utils.UtilityTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    UtilityTools utilityTools = new UtilityTools();

    @Autowired
    public ProductService(ProductRepository productRepository, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.entityManager = entityManager;
    }

    public Response createProduct(ProductRequest productRequest) {
        Product product = new Product();
        try {
            Product productCheck = productRepository.findByProductName(productRequest.getProductName());
            if (productCheck != null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_CREATE_PRODUCT_DUPLICATE);
            }

            product.setProductCode("product-00" + utilityTools.randomNumber(4))
                    .setProductName(productRequest.getProductName())
                    .setProductType(productRequest.getProductType())
                    .setProductQuantity(productRequest.getProductQuantity())
                    .setPrice(productRequest.getPrice())
                    .setStatus(Constant.PRODUCT_STATUS_ACTIVE)
                    .setDescription(productRequest.getDescription())
                    .setCreateBy(productRequest.getCreateBy())
                    .setCreateDateTime(utilityTools.getFormatsDateMilli());

            productRepository.save(product);
        } catch (ResponseException e) {
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_CREATE_PRODUCT, product);
    }

    public Response updateProduct(ProductUpdateRequest productUpdateRequest) {
        Product product;
        try {
            product = productRepository.findByProductCode(productUpdateRequest.getProductCode());
            if (product == null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_PRODUCT_NOT_FOUND);
            }

            if (productUpdateRequest.getProductName() != null && !("").equals(productUpdateRequest.getProductName()))
                product.setProductName(productUpdateRequest.getProductName());
            if (productUpdateRequest.getProductType() != null && !("").equals(productUpdateRequest.getProductType()))
                product.setProductType(productUpdateRequest.getProductType());
            if (productUpdateRequest.getProductQuantity() != null && !("").equals(productUpdateRequest.getProductQuantity()))
                product.setProductQuantity(productUpdateRequest.getProductQuantity());
            if (productUpdateRequest.getPrice() != null && !("").equals(productUpdateRequest.getPrice()))
                product.setPrice(productUpdateRequest.getPrice());
            if (productUpdateRequest.getDescription() != null && !("").equals(productUpdateRequest.getDescription()))
                product.setDescription(productUpdateRequest.getDescription());
            if (productUpdateRequest.getStatus() != null && !("").equals(productUpdateRequest.getStatus()))
                product.setStatus(productUpdateRequest.getStatus());

            product.setUpdateDateTime(utilityTools.getFormatsDateMilli());
            productRepository.save(product);
        } catch (ResponseException e) {
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_UPDATE_PRODUCT, product);
    }

    public Response deleteProduct(Integer productId) {
        try {
            productRepository.deleteById(productId);
        } catch (ResponseException e) {
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_DELETE_PRODUCT, null);
    }

    public Response searchProductByPage(ProductInquiryRequest request) {
        List<Product> productList;
        try {
            //createQuery
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Product> productRoot = criteriaQuery.from(Product.class);
            criteriaQuery.select(productRoot);

            //list condition
            List<Predicate> predicates = new ArrayList<>();
            if (request.getProductCode() != null && !("").equals(request.getProductCode())) {
                predicates.add(criteriaBuilder.like(productRoot.get("productCode"), "%" + request.getProductCode() + "%"));
            }
            if (request.getProductType() != null && !("").equals(request.getProductType())) {
                predicates.add(criteriaBuilder.like(productRoot.get("productType"), "%" + request.getProductType() + "%"));
            }
            if (request.getProductName() != null && !("").equals(request.getProductName())) {
                predicates.add(criteriaBuilder.like(productRoot.get("productName"), "%" + request.getProductName() + "%"));
            }
            if (request.getStatus() != null && !("").equals(request.getStatus())) {
                predicates.add(criteriaBuilder.equal(productRoot.get("status"), request.getStatus()));
            }
            if (request.getProductQuantity() != null && !("").equals(request.getProductQuantity())) {
                predicates.add(criteriaBuilder.equal(productRoot.get("productQuantity"), request.getProductQuantity()));
            }
            if (request.getCreateDateTimeBefore() != null && !("").equals(request.getCreateDateTimeBefore())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(productRoot.get("createDateTime"), request.getCreateDateTimeBefore()));
            }
            if (request.getCreateDateTimeEnd() != null && !("").equals(request.getCreateDateTimeEnd())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(productRoot.get("createDateTime"), request.getCreateDateTimeEnd()));
            }
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

            productList = entityManager
                    .createQuery(criteriaQuery)
                    .setMaxResults(request.getPageSize()) // pageSize
                    .setFirstResult(request.getPageNumber() * request.getPageSize()) // offset
                    .getResultList();
        } catch (ResponseException e) {
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_INQUIRY_PRODUCT, productList);
    }
}
