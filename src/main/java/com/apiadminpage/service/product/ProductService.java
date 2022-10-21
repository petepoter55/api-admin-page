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
import com.apiadminpage.validator.ValidateProduct;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private static final Logger logger = Logger.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ValidateProduct validateProduct;
    private final EntityManager entityManager;

    UtilityTools utilityTools = new UtilityTools();

    @Autowired
    public ProductService(ProductRepository productRepository, ValidateProduct validateProduct, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.validateProduct = validateProduct;
        this.entityManager = entityManager;
    }

    public Response createProduct(ProductRequest productRequest) {
        logger.info("start create product");
        logger.info("product request : " + productRequest);
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
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }
        logger.info("done create product");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_CREATE_PRODUCT, product);
    }

    public Response updateProduct(ProductUpdateRequest productUpdateRequest) {
        logger.info("start update product");
        logger.info("update request : " + productUpdateRequest);
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
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }
        logger.info("done update product");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_UPDATE_PRODUCT, product);
    }

    public Response deleteProduct(Integer productId) {
        logger.info("start delete product");
        logger.info("id : " + productId);
        try {
            productRepository.deleteById(productId);
        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        }
        logger.info("done delete product");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_DELETE_PRODUCT, null);
    }

    public Response searchProductByPage(ProductInquiryRequest request) {
        logger.info("start inquiry product");
        logger.info("inquiry request : " + request);
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
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        }
        logger.info("done inquiry product");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_INQUIRY_PRODUCT, productList);
    }

    public Response importProduct(MultipartFile file) {
        logger.info("start import product");
        logger.info("file type : " + file.getContentType());
        logger.info("file name : " + file.getOriginalFilename());
        List<ProductRequest> productList = new ArrayList<>();
        try {
            validateProduct.validateTypeExcel(file);

            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
                if (index > 0) { // because index 0 is title excel
                    XSSFRow row = worksheet.getRow(index);

                    ProductRequest productRequest = new ProductRequest();
                    productRequest.setProductName(row.getCell(1).getStringCellValue());
                    productRequest.setDescription(row.getCell(2).getStringCellValue());
                    productRequest.setProductType(row.getCell(3).getStringCellValue());
                    productRequest.setProductQuantity(String.valueOf((int) row.getCell(4).getNumericCellValue()));
                    productRequest.setPrice(String.valueOf(row.getCell(5).getNumericCellValue()));
                    productList.add(productRequest);

                    createProduct(productRequest);
                }
            }
        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (IOException | IllegalStateException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(Constant.STATUS_CODE_FAIL, e.getMessage(), null);
        }
        logger.info("done import product");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_INQUIRY_PRODUCT, productList);
    }

    public void exportProduct(HttpServletResponse response, List<Product> productList) throws ParseException, IOException {
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + "product_" + utilityTools.getFormatsDateString() + "_" + ".xlsx");
        OutputStream outStream = null;

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Product");

            // Fix header column excel
            String[] columns = {
                    "productCode",
                    "productType",
                    "productName",
                    "description",
                    "status",
                    "productQuantity",
                    "price",
                    "createDateTime"
            };

            // set style Header
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            // create header cell
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // initialize data in row
            int rowNum = 1;
            for (Product d : productList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(d.getProductCode());
                row.createCell(1).setCellValue(d.getProductType());
                row.createCell(2).setCellValue(d.getProductName());
                row.createCell(3).setCellValue(d.getDescription());
                row.createCell(4).setCellValue(d.getStatus());
                row.createCell(5).setCellValue(d.getProductQuantity());
                row.createCell(6).setCellValue(d.getPrice());
                row.createCell(7).setCellValue(utilityTools.generateDateTimeToThai(d.getCreateDateTime()));
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // output response file name
            outStream = response.getOutputStream();
            workbook.write(outStream);
            outStream.flush();
        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
        } finally {
            if (outStream != null) {
                outStream.close();
            }
        }
    }
}
