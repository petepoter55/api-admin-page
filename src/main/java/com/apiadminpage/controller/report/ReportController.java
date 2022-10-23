package com.apiadminpage.controller.report;

import com.apiadminpage.model.response.Response;
import com.apiadminpage.service.product.ProductService;
import com.apiadminpage.service.report.ReportService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/form")
public class ReportController {
    private static final Logger logger = Logger.getLogger(ReportController.class);

    private final ProductService productService;
    private final ReportService reportService;

    @Autowired
    public ReportController(ProductService productService, ReportService reportService) {
        this.productService = productService;
        this.reportService = reportService;
    }

    @ApiOperation(value = "Export Product Data to Excel", nickname = "exportProduct", notes = "Export Data Product in Excel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Business Error"),
            @ApiResponse(code = 500, message = "Internal server error occurred"),
            @ApiResponse(code = 503, message = "Service Unavailable")})
    @RequestMapping(value = "/insert-form", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public Response insertFormReport(
            @ApiParam(name = "document", value = "document file xml to be uploaded", required = true)
            @RequestPart(value = "document", required = true) MultipartFile file,
            @ApiParam(name = "version", value = "version file xml to be uploaded", required = true)
            @RequestPart(value = "version", required = true) String version,
            @ApiParam(name = "name", value = "name file xml to be uploaded", required = true)
            @RequestPart(value = "name", required = true) String name,
            @ApiParam(name = "startDate", value = "startDate file xml to be uploaded", required = true)
            @RequestPart(value = "startDate", required = true) String startDate,
            @ApiParam(name = "endDate", value = "endDate file xml to be uploaded", required = true)
            @RequestPart(value = "endDate", required = true) String endDate,
            @ApiParam(name = "createBy", value = "create By", required = false)
            @RequestPart(value = "createBy", required = false) String createBy,
            HttpServletRequest request
    ) {
        logger.info("Path =" + request.getRequestURI() + ", method = " + request.getMethod() + " INITIATED...");
        return reportService.insertReport(file, version, name, startDate, endDate, createBy);
    }


    @ApiOperation(value = "preview report jasper to PDF", nickname = "previewReportJasper", notes = "Preview report jasper to PDF")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Business Error"),
            @ApiResponse(code = 500, message = "Internal server error occurred"),
            @ApiResponse(code = 503, message = "Service Unavailable")})
    @RequestMapping(value = "/get-pdf", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void getReportPDF(
            @RequestBody(required = true) String jsonRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ParseException {
        logger.info("Path =" + request.getRequestURI() + ", method = " + request.getMethod() + " INITIATED...");
        reportService.getPDF(response, jsonRequest);
    }
}
