package com.apiadminpage.service.product;

import com.apiadminpage.entity.product.ImageProduct;
import com.apiadminpage.entity.product.Product;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.response.Response;
import com.apiadminpage.repository.product.ImageProductRepository;
import com.apiadminpage.repository.product.ProductRepository;
import com.apiadminpage.utils.UtilityTools;
import com.apiadminpage.validator.ValidateProduct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Base64;

@Service
public class ProductImageService {
    private static final Logger logger = Logger.getLogger(ProductImageService.class);
    private final ImageProductRepository imageProductRepository;
    private final ProductRepository productRepository;
    private final ValidateProduct validateProduct;

    UtilityTools utilityTools = new UtilityTools();

    @Autowired
    public ProductImageService(ImageProductRepository imageProductRepository, ProductRepository productRepository, ValidateProduct validateProduct) {
        this.imageProductRepository = imageProductRepository;
        this.productRepository = productRepository;
        this.validateProduct = validateProduct;
    }

    public Response createImage(String productCode, MultipartFile file, String createBy) {
        logger.info("start create image product");
        logger.info("file name : " + file.getOriginalFilename());
        logger.info("file type : " + file.getContentType());
        logger.info("productCode : " + productCode);

        ImageProduct imageProduct = new ImageProduct();
        try {
            validateProduct.validateImage(file);
            validateProduct.validateProductCode(productCode);

            Product product = productRepository.findByProductCode(productCode);
            if (product == null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_PRODUCT_NOT_FOUND);
            }

            ImageProduct imageProductCheck = imageProductRepository.findByProductCode(productCode);
            if (imageProductCheck != null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_CREATE_IMAGE_PRODUCT_DUPLICATE);
            }

            imageProduct.setProductCode(productCode);
            imageProduct.setName(file.getOriginalFilename());
            imageProduct.setImage(Base64.getEncoder().encode(file.getBytes()));
            imageProduct.setCreateBy(createBy);
            imageProduct.setCreateDateTime(utilityTools.getFormatsDateMilli());
            imageProductRepository.save(imageProduct);

        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (IOException | ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }

        logger.info("done create image product");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_CREATE_IMAGE_PRODUCT, imageProduct);
    }

    public Response inquiryImage(String productCode) {
        logger.info("start get image by productCode");
        logger.info("productCode : " + productCode);

        ImageProduct imageProduct;
        byte[] image = null;
        try {
            validateProduct.validateProductCode(productCode);

            imageProduct = imageProductRepository.findByProductCode(productCode);
            if (imageProduct == null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_IMAGE_PRODUCT_NOT_FOUND);
            }

            image = Base64.getDecoder().decode(imageProduct.getImage());
        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        }

        logger.info("done get image by productCode");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_GET_IMAGE_PRODUCT, image);
    }

    public Response updateImageProduct(String productCode, MultipartFile file, String updateBy) {
        logger.info("start update image product");
        logger.info("file name : " + file.getOriginalFilename());
        logger.info("file type : " + file.getContentType());
        logger.info("productCode : " + productCode);

        ImageProduct imageProduct = new ImageProduct();
        try {
            validateProduct.validateImage(file);
            validateProduct.validateProductCode(productCode);

            Product product = productRepository.findByProductCode(productCode);
            if (product == null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_PRODUCT_NOT_FOUND);
            }

            imageProduct = imageProductRepository.findByProductCode(productCode);
            if (imageProduct == null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_IMAGE_PRODUCT_NOT_FOUND);
            }

            imageProduct.setProductCode(productCode);
            imageProduct.setName(file.getOriginalFilename());
            imageProduct.setImage(Base64.getEncoder().encode(file.getBytes()));
            imageProduct.setUpdateBy(updateBy);
            imageProduct.setUpdateDateTime(utilityTools.getFormatsDateMilli());
            imageProductRepository.save(imageProduct);

        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (IOException | ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }

        logger.info("done update image product");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_UPDATE_IMAGE_PRODUCT, imageProduct);
    }
}
