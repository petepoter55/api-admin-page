package com.apiadminpage.controller.account;

import com.apiadminpage.model.response.Response;
import com.apiadminpage.service.account.AccountImageService;
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
@RequestMapping("/image-account")
public class AccountImageController {
    private static final Logger logger = Logger.getLogger(AccountImageController.class);

    @Autowired
    private AccountImageService accountImageService;

    @ApiOperation(value = "Create Image Account", nickname = "createImageAccount", notes = "Create Image Account in database")
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
    public @ResponseBody Response createImageAccount(
            @ApiParam(name = "userId", value = "User ID", example = "2", required = true)
            @RequestPart(value = "userId", required = true) String userId,
            @ApiParam(name = "createBy", value = "Create By", example = "admin")
            @RequestPart(value = "createBy", required = false) String createBy,
            @ApiParam(name = "image", value = "image file to be uploaded", required = true)
            @RequestPart(value = "image", required = true) MultipartFile file,
            HttpServletRequest request
    ) {
        logger.info("Path =" + request.getRequestURI() + ", method = " + request.getMethod() + " INITIATED...");
        return accountImageService.insertImageAccount(userId, file, createBy);
    }

    @ApiOperation(value = "inquiry Image By UserID", nickname = "inquiryImageByUserID", notes = "Inquiry Image By UserID")
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
    public @ResponseBody Response getImageAccount(
            @ApiParam(name = "userId", value = "User ID", example = "2", required = true)
            @RequestParam(value = "userId", required = true) String userId,
            HttpServletRequest request
    ) {
        logger.info("Path =" + request.getRequestURI() + ", method = " + request.getMethod() + " INITIATED...");
        return accountImageService.getImageAccount(userId);
    }

    @ApiOperation(value = "Update Image Account", nickname = "updateImageAccount", notes = "Update Image Account in database")
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
    public @ResponseBody Response updateImageAccount(
            @ApiParam(name = "userId", value = "User ID", example = "2", required = true)
            @RequestPart(value = "userId", required = true) String userId,
            @ApiParam(name = "updateBy", value = "Create By", example = "admin")
            @RequestPart(value = "updateBy", required = false) String updateBy,
            @ApiParam(name = "image", value = "image file to be uploaded", required = true)
            @RequestPart(value = "image", required = true) MultipartFile file,
            HttpServletRequest request
    ) {
        logger.info("Path =" + request.getRequestURI() + ", method = " + request.getMethod() + " INITIATED...");
        return accountImageService.updateImageAccount(userId, file, updateBy);
    }
}
