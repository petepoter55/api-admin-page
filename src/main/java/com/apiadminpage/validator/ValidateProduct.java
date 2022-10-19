package com.apiadminpage.validator;

import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;


@Component
public class ValidateProduct {
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

    public void validateProductCode(String productCode) {
        if (!StringUtils.isNotEmpty(productCode)) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_PRODUCT_CODE_NULL);
        }

        if (productCode.length() > 50) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_PRODUCT_CODE_SIZE);
        }
    }
}
