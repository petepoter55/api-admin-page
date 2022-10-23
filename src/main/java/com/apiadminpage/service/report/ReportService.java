package com.apiadminpage.service.report;

import com.apiadminpage.entity.report.ManageReport;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.response.Response;
import com.apiadminpage.repository.report.ManageReportRepository;
import com.apiadminpage.utils.UtilityTools;
import com.apiadminpage.validator.ValidateReport;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.JRException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Map;

@Service
public class ReportService {
    private static final Logger logger = Logger.getLogger(ReportService.class);

    private final ManageReportRepository manageReportRepository;
    private final ValidateReport validateReport;

    UtilityTools utilityTools = new UtilityTools();

    @Autowired
    public ReportService(ManageReportRepository manageReportRepository, ValidateReport validateReport) {
        this.manageReportRepository = manageReportRepository;
        this.validateReport = validateReport;
    }

    public Response insertReport(MultipartFile file, String version, String name, String startDate, String endDate, String createBy) {
        ManageReport manageReport = new ManageReport();
        try {
            validateReport.validateXML(file);
            validateReport.validateInputReport(version, name, startDate, endDate);

            ManageReport manageReportCheck = manageReportRepository.findByName(name);
            if (manageReportCheck != null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_CREATE_IMAGE_PRODUCT_DUPLICATE);
            }
            manageReport.setVersion(version);
            manageReport.setName(name);
            manageReport.setForm(file.getBytes());
            manageReport.setStartDateTime(startDate);
            manageReport.setEndDateTime(endDate);
            manageReport.setCreateBy(createBy);
            manageReport.setCreateDateTime(utilityTools.getFormatsDateMilli());
            manageReportRepository.save(manageReport);

        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (IOException | ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }

        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_CREATE_IMAGE_PRODUCT, manageReport);
    }

    public void getPDF(HttpServletResponse response, String json) throws IOException, ParseException {
        response.setHeader("Content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + "product_" + utilityTools.getFormatsDateString() + "_" + ".pdf");
        OutputStream outStream = null;
        InputStream reportStream = null;
        byte[] outputPDF;

        try {
            Map map = new ObjectMapper().readValue(json, Map.class);
            ManageReport manageReport = manageReportRepository.findByName(map.get("name").toString());
            if (manageReport == null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_DATA_REPORT_NOT_FOUND);
            }

            reportStream = new ByteArrayInputStream(manageReport.getForm());
            outputPDF = utilityTools.jasperToPDF(reportStream, json);

            outStream = response.getOutputStream();
            outStream.write(outputPDF);
            outStream.flush();
        } catch (ResponseException | JRException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            throw new ResponseException(Constant.STATUS_CODE_ERROR, e.getMessage());
        } finally {
            if (outStream != null) {
                outStream.close();
            }
            if (reportStream != null) {
                reportStream.close();
            }
        }
    }
}
