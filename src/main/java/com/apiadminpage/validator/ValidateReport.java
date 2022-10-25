package com.apiadminpage.validator;

import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Component
public class ValidateReport {
    public void validateTypeExcel(MultipartFile file) {
        if (!(Constant.TYPE_FILE_EXCEL).equals(file.getContentType())) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_FILE_TYPE_INVALID);
        }
    }

    public void validateImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_FILE_IMAGE_NULL);
        }

        if (Arrays.stream(Constant.TYPE_FILE_IMAGE).noneMatch(file.getContentType()::equals)) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_FILE_IMAGE_TYPE_INVALID);
        }
    }

    public void validateTypeXML(MultipartFile file, String type) {
        if (((Constant.TYPE_REPORT_INSERT.equals(type) || Constant.TYPE_REPORT_UPDATE.equals(type)) && !file.isEmpty())) {
            if (!Constant.TYPE_FILE_XML.equals(file.getContentType())) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_FILE_XML_TYPE_INVALID);
            }
        }

    }

    public void validateFileInput(MultipartFile file) {
        if (file.isEmpty())
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_FILE_XML_NOT_FOUND);
    }

    public void validateInputReport(String version, String name, String startDate, String endDate) {
        if (StringUtils.isEmpty(version)) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_VERSION_REPORT_NULL);
        }
        if (StringUtils.isEmpty(name)) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_NAME_REPORT_NULL);
        }
        if (StringUtils.isEmpty(startDate)) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_START_DATE_REPORT_NULL);
        }
        if (StringUtils.isEmpty(endDate)) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_END_DATE_REPORT_NULL);
        }
    }

    public void validateReportId(String reportId) {
        if (StringUtils.isEmpty(reportId)) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_REPORT_ID_REPORT_NULL);
        }
    }
}
