package com.apiadminpage.controller.product;

import com.apiadminpage.model.response.Response;
import com.apiadminpage.service.product.ProductImageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/image-product")
public class ProductImageController {
    private static final Logger logger = Logger.getLogger(ProductImageController.class);

    private final ProductImageService productImageService;

    @Autowired
    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @ApiOperation(value = "Create Image Product", nickname = "createImageProduct", notes = "Create Image Product in database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Business Error"),
            @ApiResponse(code = 500, message = "Internal server error occurred"),
            @ApiResponse(code = 503, message = "Service Unavailable")})
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Response createImageProduct(
            @ApiParam(name = "productCode", value = "Product Code", example = "product-007961", required = true)
            @RequestPart(value = "productCode", required = true) String productCode,
            @ApiParam(name = "createBy", value = "Create By", example = "admin")
            @RequestPart(value = "createBy", required = false) String createBy,
            @ApiParam(name = "image", value = "image file to be uploaded", required = true)
            @RequestPart(value = "image", required = true) MultipartFile file,
            HttpServletRequest request
    ) {
        logger.info("Path =" + request.getRequestURI() + ", method = " + request.getMethod() + " INITIATED...");
        return productImageService.createImage(productCode, file, createBy);
    }

    @ApiOperation(value = "inquiry Image By ProductCode", nickname = "inquiryImageByProductCode", notes = "Inquiry Image By ProductCode")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Business Error"),
            @ApiResponse(code = 500, message = "Internal server error occurred"),
            @ApiResponse(code = 503, message = "Service Unavailable")})
    @RequestMapping(value = "/getImage", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Response getImageProduct(
            @ApiParam(name = "productCode", value = "Product Code", example = "product-007961", required = true)
            @RequestParam(value = "productCode", required = true) String productCode,
            HttpServletRequest request
    ) {
        logger.info("Path =" + request.getRequestURI() + ", method = " + request.getMethod() + " INITIATED...");
        return productImageService.inquiryImage(productCode);
    }

    @ApiOperation(value = "Update Image Product", nickname = "updateImageProduct", notes = "Update Image Product in database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Business Error"),
            @ApiResponse(code = 500, message = "Internal server error occurred"),
            @ApiResponse(code = 503, message = "Service Unavailable")})
    @RequestMapping(value = "/update-image", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Response updateImageProduct(
            @ApiParam(name = "productCode", value = "Product Code", example = "product-007961", required = true)
            @RequestPart(value = "productCode", required = true) String productCode,
            @ApiParam(name = "updateBy", value = "Create By", example = "admin")
            @RequestPart(value = "updateBy", required = false) String updateBy,
            @ApiParam(name = "image", value = "image file to be uploaded", required = true)
            @RequestPart(value = "image", required = true) MultipartFile file,
            HttpServletRequest request
    ) {
        logger.info("Path =" + request.getRequestURI() + ", method = " + request.getMethod() + " INITIATED...");
        return productImageService.updateImageProduct(productCode, file, updateBy);
    }
}
