package com.apiadminpage.validator;

import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.request.account.AccountRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Component
public class ValidateAccount {
    public void validateRequestAccount(AccountRequest accountRequest) {
        if (!accountRequest.getPassword().equals(accountRequest.getConfirmPassword()))
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_ACCOUNT_REQUEST_PASSWORD_INVALID);

        if (!accountRequest.getEmail().equals(accountRequest.getConfirmEmail()))
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_ACCOUNT_REQUEST_EMAIL_INVALID);
    }

    public void validateImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_FILE_IMAGE_NULL);
        }

        if (Arrays.stream(Constant.TYPE_FILE_IMAGE).noneMatch(file.getContentType()::equals)) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_FILE_IMAGE_TYPE_INVALID);
        }
    }

    public void validateUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_ACCOUNT_USERID_NULL);
        }

        if (userId.length() > 5) {
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_ACCOUNT_USERID_SIZE);
        }
    }
}
