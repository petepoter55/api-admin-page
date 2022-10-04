package com.apiadminpage.validator;

import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.request.account.AccountRequest;
import org.springframework.stereotype.Component;

@Component
public class ValidateAccount {
    public void validateRequestAccount(AccountRequest accountRequest) {
        if (!accountRequest.getPassword().equals(accountRequest.getConfirmPassword()))
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_ACCOUNT_REQUEST_PASSWORD_INVALID);

        if (!accountRequest.getEmail().equals(accountRequest.getConfirmEmail()))
            throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_ACCOUNT_REQUEST_EMAIL_INVALID);
    }
}
