package com.apiadminpage.service.account;

import com.apiadminpage.entity.account.Account;
import com.apiadminpage.entity.account.ImageAccount;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.response.Response;
import com.apiadminpage.repository.account.AccountRepository;
import com.apiadminpage.repository.account.ImageAccountRepository;
import com.apiadminpage.utils.UtilityTools;
import com.apiadminpage.validator.ValidateAccount;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Base64;
import java.util.Optional;

@Service
public class AccountImageService {
    private static final Logger logger = Logger.getLogger(AccountImageService.class);

    private final ImageAccountRepository imageAccountRepository;
    private final AccountRepository accountRepository;
    private final ValidateAccount validateAccount;

    UtilityTools utilityTools = new UtilityTools();

    @Autowired
    public AccountImageService(ImageAccountRepository imageAccountRepository, AccountRepository accountRepository, ValidateAccount validateAccount) {
        this.imageAccountRepository = imageAccountRepository;
        this.accountRepository = accountRepository;
        this.validateAccount = validateAccount;
    }

    public Response insertImageAccount(String userId, MultipartFile file, String createBy) {
        logger.info("start create image account");
        logger.info("file name : " + file.getOriginalFilename());
        logger.info("file type : " + file.getContentType());
        logger.info("userID : " + userId);

        ImageAccount imageAccount = new ImageAccount();
        try {
            validateAccount.validateUserId(userId);
            validateAccount.validateImage(file);

            Optional<Account> account = accountRepository.findById(Integer.parseInt(userId));
            if (!account.isPresent()) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
            }

            ImageAccount imageAccountCheck = imageAccountRepository.findByUserId(userId);
            if (imageAccountCheck != null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_CREATE_IMAGE_ACCOUNT_DUPLICATE);
            }

            imageAccount.setName(file.getOriginalFilename());
            imageAccount.setUserId(userId);
            imageAccount.setImage(Base64.getEncoder().encode(file.getBytes()));
            imageAccount.setCreateBy(createBy);
            imageAccount.setCreateDateTime(utilityTools.getFormatsDateMilli());
            imageAccountRepository.save(imageAccount);

        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (IOException | ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }
        logger.info("done create image account");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_CREATE_IMAGE_ACCOUNT, imageAccount);
    }

    public Response getImageAccount(String userId) {
        logger.info("start get image by userId");
        logger.info("userId : " + userId);

        ImageAccount imageAccount;
        byte[] image = null;
        try {
            validateAccount.validateUserId(userId);

            imageAccount = imageAccountRepository.findByUserId(userId);
            if (imageAccount == null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_IMAGE_ACCOUNT_NOT_FOUND);
            }

            image = Base64.getDecoder().decode(imageAccount.getImage());
        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        }

        logger.info("done get image by userId");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_GET_IMAGE_ACCOUNT, image);
    }

    public Response updateImageAccount(String userId, MultipartFile file, String updateBy) {
        logger.info("start update image account");
        logger.info("file name : " + file.getOriginalFilename());
        logger.info("file type : " + file.getContentType());
        logger.info("userID : " + userId);

        ImageAccount imageAccount = new ImageAccount();
        try {
            validateAccount.validateUserId(userId);
            validateAccount.validateImage(file);

            Optional<Account> account = accountRepository.findById(Integer.parseInt(userId));
            if (!account.isPresent()) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
            }

            imageAccount = imageAccountRepository.findByUserId(userId);
            if (imageAccount == null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_CREATE_IMAGE_ACCOUNT_NOT_FOUND);
            }

            imageAccount.setName(file.getOriginalFilename());
            imageAccount.setUserId(userId);
            imageAccount.setImage(Base64.getEncoder().encode(file.getBytes()));
            imageAccount.setUpdateBy(updateBy);
            imageAccount.setUpdateDateTime(utilityTools.getFormatsDateMilli());
            imageAccountRepository.save(imageAccount);

        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (IOException | ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }
        logger.info("done update image account");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_UPDATE_IMAGE_ACCOUNT, imageAccount);
    }
}
