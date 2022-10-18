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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
public class AccountInfoService {
    private static final Logger logger = Logger.getLogger(AccountInfoService.class);

    private final AccountInfoRepository accountInfoRepository;
    private final AccountRepository accountRepository;

    UtilityTools utilityTools = new UtilityTools();

    @Autowired
    public AccountInfoService(AccountInfoRepository accountInfoRepository, AccountRepository accountRepository) {
        this.accountInfoRepository = accountInfoRepository;
        this.accountRepository = accountRepository;
    }

    public Response createAccountInfo(AccountInfoRequest accountInfoRequest) {
        logger.info("start create accountInfo");
        logger.info("accountInfo request : " + accountInfoRequest);

        AccountInfo accountInfo = new AccountInfo();
        try {
            Optional<Account> account = accountRepository.findById(Integer.parseInt(accountInfoRequest.getUserId()));
            if (!account.isPresent()) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
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
}
