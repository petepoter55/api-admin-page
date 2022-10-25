package com.apiadminpage.service.account;

import com.apiadminpage.entity.account.Account;
import com.apiadminpage.entity.account.AccountInfo;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.request.account.AccountInfoRequest;
import com.apiadminpage.model.response.Response;
import com.apiadminpage.repository.account.AccountInfoRepository;
import com.apiadminpage.repository.account.AccountRepository;
import com.apiadminpage.utils.UtilityTools;
import com.apiadminpage.validator.ValidateAccount;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountInfoService {
    private static final Logger logger = Logger.getLogger(AccountInfoService.class);

    private final AccountInfoRepository accountInfoRepository;
    private final AccountRepository accountRepository;
    private final ValidateAccount validateAccount;

    UtilityTools utilityTools = new UtilityTools();

    @Autowired
    public AccountInfoService(AccountInfoRepository accountInfoRepository, AccountRepository accountRepository, ValidateAccount validateAccount) {
        this.accountInfoRepository = accountInfoRepository;
        this.accountRepository = accountRepository;
        this.validateAccount = validateAccount;
    }

    public Response createAccountInfo(AccountInfoRequest accountInfoRequest) {
        logger.info("start create accountInfo");
        logger.info("accountInfo request : " + accountInfoRequest.toString());

        AccountInfo accountInfo = new AccountInfo();
        try {
            Optional<Account> account = accountRepository.findById(Integer.parseInt(accountInfoRequest.getUserId()));
            if (account.isPresent()) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_DATA_ACCOUNT_INFO_DUPLICATE);
            }

            accountInfo.setUserId(accountInfoRequest.getUserId())
                    .setBirthDate(accountInfoRequest.getBirthDate())
                    .setCitizenId(accountInfoRequest.getCitizenId())
                    .setDistrict(accountInfoRequest.getDistrict())
                    .setPhone(accountInfoRequest.getPhone())
                    .setProvince(accountInfoRequest.getProvince())
                    .setCreateBy(accountInfoRequest.getCreateBy())
                    .setCreateDateTime(utilityTools.getFormatsDateMilli());

            accountInfoRepository.save(accountInfo);
        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }

        logger.info("done create accountInfo");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_REGISTER_ACCOUNT, accountInfo);
    }

    public AccountInfo inquiryAccountInfo(String userId) {
        AccountInfo accountInfo = new AccountInfo();
        try {
            accountInfo = accountInfoRepository.findByUserId(userId);
        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
        }
        return accountInfo;
    }

    public Response updateAccountInfo(AccountInfoRequest accountInfoRequest) {
        logger.info("start update accountInfo");
        logger.info("accountInfo request : " + accountInfoRequest.toString());
        AccountInfo accountInfo;
        Date currentDate;

        try {
            accountInfo = accountInfoRepository.findByUserId(accountInfoRequest.getUserId());
            if (accountInfo == null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_DATA_ACCOUNT_INFO_NOT_FOUND);
            }

            currentDate = utilityTools.getFormatsDateMilli();
            if (accountInfoRequest.getPhone() != null && !("").equals(accountInfoRequest.getPhone()))
                accountInfo.setPhone(accountInfoRequest.getPhone());
            if (accountInfoRequest.getCitizenId() != null && !("").equals(accountInfoRequest.getCitizenId()))
                accountInfo.setCitizenId(accountInfoRequest.getCitizenId());
            if (accountInfoRequest.getProvince() != null && !("").equals(accountInfoRequest.getProvince()))
                accountInfo.setProvince(accountInfoRequest.getProvince());
            if (accountInfoRequest.getDistrict() != null && !("").equals(accountInfoRequest.getDistrict()))
                accountInfo.setDistrict(accountInfoRequest.getDistrict());
            if (accountInfoRequest.getBirthDate() != null && !("").equals(accountInfoRequest.getBirthDate()))
                accountInfo.setBirthDate(accountInfoRequest.getBirthDate());

            accountInfo.setUpdateBy(accountInfoRequest.getUpdateBy());
            accountInfo.setUpdateDateTime(currentDate);
            accountInfoRepository.save(accountInfo);

        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }
        logger.info("done update accountInfo");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_REGISTER_ACCOUNT, accountInfo);
    }
}
