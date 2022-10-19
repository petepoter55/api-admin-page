package com.apiadminpage.controller.account;

import com.apiadminpage.controller.product.ProductImageController;
import com.apiadminpage.entity.account.AccountInfo;
import com.apiadminpage.model.request.account.AccountInfoRequest;
import com.apiadminpage.model.response.Response;
import com.apiadminpage.service.account.AccountInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/account-info")
public class AccountInfoController {
    private static final Logger logger = Logger.getLogger(AccountInfoController.class);

    private final AccountInfoService accountInfoService;

    @Autowired
    public AccountInfoController(AccountInfoService accountInfoService) {
        this.accountInfoService = accountInfoService;
    }

    @ApiOperation(value = "Create Account Info", nickname = "createAccountInfo", notes = "Create Account Info in database")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Business Error"),
            @ApiResponse(code = 500, message = "Internal server error occurred"),
            @ApiResponse(code = 503, message = "Service Unavailable")})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Response createAccountInfo(
            @Valid @RequestBody(required = true) AccountInfoRequest accountInfoRequest,
            HttpServletRequest request
    ) {
        logger.info("Path =" + request.getRequestURI() + ", method = " + request.getMethod() + " INITIATED...");
        return accountInfoService.createAccountInfo(accountInfoRequest);
    }

    @ApiOperation(value = "Inquiry Account Info By User Id", nickname = "inquiryAccountInfoByUserId", notes = "Inquiry Account Info By User Id")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Business Error"),
            @ApiResponse(code = 500, message = "Internal server error occurred"),
            @ApiResponse(code = 503, message = "Service Unavailable")})
    @RequestMapping(value = "/inquiry/{userid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody AccountInfo inquiryAccountInfo(
            @PathVariable(value = "userid") String userid,
            HttpServletRequest request
    ) {
        logger.info("Path =" + request.getRequestURI() + ", method = " + request.getMethod() + " INITIATED...");
        return accountInfoService.inquiryAccountInfo(userid);
    }
}
