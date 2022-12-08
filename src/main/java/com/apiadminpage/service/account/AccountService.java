package com.apiadminpage.service.account;

import com.apiadminpage.entity.account.Account;
import com.apiadminpage.entity.account.AccountInfo;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.request.account.AccountLoginRequest;
import com.apiadminpage.model.request.account.AccountRequest;
import com.apiadminpage.model.request.account.AccountUpdateRequest;
import com.apiadminpage.model.request.account.InquiryAccountRequest;
import com.apiadminpage.model.request.log.LogRequest;
import com.apiadminpage.model.response.Response;
import com.apiadminpage.model.response.account.AccountLoginResponse;
import com.apiadminpage.model.response.account.ResponseAccount;
import com.apiadminpage.model.response.account.ResponseAccountInfo;
import com.apiadminpage.repository.account.AccountInfoRepository;
import com.apiadminpage.repository.account.AccountRepository;
import com.apiadminpage.service.jwt.JWTService;
import com.apiadminpage.service.log.LogService;
import com.apiadminpage.utils.UtilityTools;
import com.apiadminpage.validator.ValidateAccount;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private static final Logger logger = Logger.getLogger(AccountService.class);

    private final AccountInfoRepository accountInfoRepository;
    private final AccountRepository accountRepository;
    private final ValidateAccount validateAccount;
    private final LogService logService;
    private final AccountInfoService accountInfoService;
    private final EntityManager entityManager;
    private final JWTService jwtService;

    UtilityTools utilityTools = new UtilityTools();

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountInfoRepository accountInfoRepository, ValidateAccount validateAccount, LogService logService, AccountInfoService accountInfoService, EntityManager entityManager, JWTService jwtService) {
        this.accountInfoRepository = accountInfoRepository;
        this.accountRepository = accountRepository;
        this.validateAccount = validateAccount;
        this.logService = logService;
        this.accountInfoService = accountInfoService;
        this.entityManager = entityManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public Response createAccount(AccountRequest accountRequest) {
        logger.info("start create account");
        logger.info("create request : " + accountRequest);
        Account account = new Account();

        Date currentDate = null;
        boolean status = true;
        try {
            validateAccount.validateRequestAccount(accountRequest);

            Account accountCheck = accountRepository.findByUsername(accountRequest.getUsername());
            if (accountCheck != null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_REGISTER_CHECKDATA_DUPLICATE);
            }
            currentDate = utilityTools.getFormatsDateMilli();

            account.setUsername(accountRequest.getUsername())
                    .setPassword(utilityTools.hashSha256(accountRequest.getPassword()))
                    .setFirstname(accountRequest.getFirstname())
                    .setLastname(accountRequest.getLastname())
                    .setEmail(accountRequest.getEmail())
                    .setRole(accountRequest.getRole())
                    .setCreateBy(accountRequest.getCreateBy())
                    .setDelFlag(accountRequest.getDelFlag())
                    .setCreateDateTime(currentDate);
            accountRepository.save(account);

        } catch (ResponseException e) {
            status = false;
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | ParseException e) {
            status = false;
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        } finally {
            String type = Constant.TYPE_REGISTER_SUCCESS;
            if (!status) {
                type = Constant.TYPE_REGISTER_FAILED;
            }
            logService.insertLog(new LogRequest(accountRequest.getCreateBy(), type, currentDate));
        }
        logger.info("done create account");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_REGISTER_ACCOUNT, account);
    }

    @Transactional
    public Response updateAccount(AccountUpdateRequest accountUpdateRequest) {
        logger.info("start update account");
        logger.info("update request : " + accountUpdateRequest);
        Account account;
        Date currentDate = null;
        boolean status = true;

        try {
            Optional<Account> accountOptional = this.accountRepository.findById(accountUpdateRequest.getId());
            if (!accountOptional.isPresent()) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
            }

            account = accountOptional.get();
            currentDate = utilityTools.getFormatsDateMilli();

            if (accountUpdateRequest.getLastname() != null && !("").equals(accountUpdateRequest.getLastname()))
                account.setLastname(accountUpdateRequest.getLastname());
            if (accountUpdateRequest.getFirstname() != null && !("").equals(accountUpdateRequest.getFirstname()))
                account.setFirstname(accountUpdateRequest.getFirstname());
            if (accountUpdateRequest.getEmail() != null && !("").equals(accountUpdateRequest.getEmail()))
                account.setEmail(accountUpdateRequest.getEmail());
            if (accountUpdateRequest.getRole() != null && !("").equals(accountUpdateRequest.getRole()))
                account.setRole(accountUpdateRequest.getRole());
            if (accountUpdateRequest.getUpdateBy() != null && !("").equals(accountUpdateRequest.getUpdateBy()))
                account.setUpdateBy(accountUpdateRequest.getUpdateBy());
            if (accountUpdateRequest.getDelFlag() != null && !("").equals(accountUpdateRequest.getDelFlag()))
                account.setDelFlag(accountUpdateRequest.getDelFlag());

            account.setUpdateDateTime(utilityTools.getFormatsDateMilli());

            accountRepository.save(account);
        } catch (ResponseException e) {
            status = false;
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            status = false;
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        } finally {
            String type = Constant.TYPE_UPDATE_SUCCESS;
            if (!status) {
                type = Constant.TYPE_UPDATE_FAILED;
            }
            logService.insertLog(new LogRequest(accountUpdateRequest.getUsername(), type, currentDate));
        }

        logger.info("done update account");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_UPDATE_ACCOUNT, account);
    }

    public Response deleteAccount(Integer id) {
        logger.info("start delete account");
        logger.info("userId : " + id);

        Account account;
        try {
            Optional<Account> accountOptional = this.accountRepository.findById(id);
            if (!accountOptional.isPresent()) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_UPDATE_DATA_NOT_FOUND);
            }

            account = accountOptional.get();
            account.setDelFlag(false);
            account.setUpdateDateTime(utilityTools.getFormatsDateMilli());

            accountRepository.save(account);
        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }

        logger.info("done delete account");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_DELETE_ACCOUNT, null);
    }

    public Response inquiryAccountById(Integer id) {
        logger.info("start inquiry account by id");
        logger.info("userId : " + id);

        ResponseAccount responseAccount = new ResponseAccount();
        try {
            Optional<Account> accountOptional = accountRepository.findById(id);
            if (!accountOptional.isPresent()) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_INQUIRY_DATA_NOT_FOUND);
            }

            responseAccount = mapInquiryAccount(accountOptional.get());

        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }

        logger.info("done inquiry account by id");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_INQUIRY_ACCOUNT, responseAccount);
    }

    public Response inquiryAccountAll() {
        List<ResponseAccount> responseAccountList = new ArrayList<>();
        ResponseAccount responseAccount = new ResponseAccount();
        try {
            List<Account> accountList = accountRepository.findAll();
            if (accountList.size() < 0) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_INQUIRY_DATA_NOT_FOUND);
            }
            for (Account account : accountList) {
                responseAccount = mapInquiryAccount(account);
                responseAccountList.add(responseAccount);
            }
        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }

        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_INQUIRY_ACCOUNT, responseAccountList);
    }

    public Response inquiryByPage(InquiryAccountRequest request) {
        logger.info("start inquiry account");
        logger.info("inquiry request : " + request);

        List<Account> accountList;

        try {
            accountList = searchAccountByPage(request);
            if (accountList.size() < 0) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_INQUIRY_DATA_NOT_FOUND);
            }
        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        }

        logger.info("done inquiry account");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_INQUIRY_ACCOUNT, accountList);
    }

    public ResponseAccount mapInquiryAccount(Account account) throws ParseException {
        ResponseAccount responseAccount = new ResponseAccount();

        responseAccount.setFirstname(account.getFirstname());
        responseAccount.setLastname(account.getLastname());
        responseAccount.setEmail(account.getEmail());
        responseAccount.setUsername(account.getUsername());
        responseAccount.setRole(account.getRole());
        responseAccount.setDelFlag(account.getDelFlag());
        responseAccount.setCreateDateTime(utilityTools.generateDateTimeToThai(account.getCreateDateTime()));
        responseAccount.setUpdateDateTime(account.getUpdateDateTime());

        AccountInfo accountInfo = accountInfoService.inquiryAccountInfo(String.valueOf(account.getId()));
        if (accountInfo != null) {
            ResponseAccountInfo responseAccountInfo = new ResponseAccountInfo();
            responseAccountInfo.setUserId(accountInfo.getUserId());
            responseAccountInfo.setDistrict(accountInfo.getDistrict());
            responseAccountInfo.setPhone(accountInfo.getPhone());
            responseAccountInfo.setProvince(accountInfo.getProvince());
            responseAccountInfo.setCitizenId(accountInfo.getCitizenId());
            responseAccountInfo.setBirthDate(accountInfo.getBirthDate());
            responseAccountInfo.setCreateDateTime(accountInfo.getCreateDateTime());
            responseAccountInfo.setUpdateDateTime(accountInfo.getUpdateDateTime());

            responseAccount.setResponseAccountInfo(responseAccountInfo);
        }
        return responseAccount;
    }

    public List<Account> searchAccountByPage(InquiryAccountRequest request) {
        List<Account> accountList;
        try {
            //createQuery
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
            Root<Account> accountRoot = criteriaQuery.from(Account.class);
            criteriaQuery.select(accountRoot);

            //list condition
            List<Predicate> predicates = new ArrayList<>();
            if (request.getUsername() != null && !("").equals(request.getUsername())) {
                predicates.add(criteriaBuilder.like(accountRoot.get("username"), "%" + request.getUsername() + "%"));
            }
            if (request.getFirstname() != null && !("").equals(request.getFirstname())) {
                predicates.add(criteriaBuilder.like(accountRoot.get("firstname"), "%" + request.getFirstname() + "%"));
            }
            if (request.getLastname() != null && !("").equals(request.getLastname())) {
                predicates.add(criteriaBuilder.like(accountRoot.get("lastname"), "%" + request.getLastname() + "%"));
            }
            if (request.getEmail() != null && !("").equals(request.getEmail())) {
                predicates.add(criteriaBuilder.like(accountRoot.get("email"), "%" + request.getEmail() + "%"));
            }
            if (request.getRole() != null && !("").equals(request.getRole())) {
                predicates.add(criteriaBuilder.equal(accountRoot.get("role"), request.getRole()));
            }
            if (request.getCreateDateTimeBefore() != null && !("").equals(request.getCreateDateTimeBefore())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(accountRoot.get("createDateTime"), request.getCreateDateTimeBefore()));
            }
            if (request.getCreateDateTimeEnd() != null && !("").equals(request.getCreateDateTimeEnd())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(accountRoot.get("createDateTime"), request.getCreateDateTimeEnd()));
            }

            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
            accountList = entityManager
                    .createQuery(criteriaQuery)
                    .setMaxResults(request.getPageSize()) // pageSize
                    .setFirstResult(request.getPageNumber() * request.getPageSize()) // offset
                    .getResultList();

        } catch (ResponseException e) {
            throw new RuntimeException();
        }

        return accountList;
    }

    /**
     * pattern password = ^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$
     * At least one upper case English letter, (?=.*?[A-Z])
     * At least one lower case English letter, (?=.*?[a-z])
     * At least one digit, (?=.*?[0-9])
     * At least one special character, (?=.*?[#?!@$%^&*-])
     * Minimum eight in length .{8,} (with the anchors)
     */
    public Response login(AccountLoginRequest accountLoginRequest) {
        logger.info("start login");
        logger.info("username : " + accountLoginRequest.getUsername());
        AccountLoginResponse accountLoginResponse = new AccountLoginResponse();
        try {
            Account account = accountRepository.findByUsername(accountLoginRequest.getUsername());
            if (account == null) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_LOGIN_DATA_NOT_FOUND);
            }

            logger.info("hashPassword DB : " + account.getPassword());
            logger.info("hashPassword Request : " + utilityTools.hashSha256(accountLoginRequest.getPassword()));
            boolean checkPassword = utilityTools.checkOldPassword(account.getPassword(), utilityTools.hashSha256(accountLoginRequest.getPassword()));
            logger.info("checkPassword : " + checkPassword);
            if (!checkPassword) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_LOGIN_PASSWORD_INVALID);
            }

            accountLoginResponse = mapAccountLoginResponse(account, jwtService.generateToken(account.getId()));

        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | ParseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }

        logger.info("done login..");
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_UPDATE_ACCOUNT, accountLoginResponse);
    }

    public AccountLoginResponse mapAccountLoginResponse(Account account, String token) throws ParseException {
        AccountLoginResponse accountLoginResponse = new AccountLoginResponse();

        accountLoginResponse.setId(account.getId());
        accountLoginResponse.setUsername(account.getUsername());
        accountLoginResponse.setFirstname(account.getFirstname());
        accountLoginResponse.setLastname(account.getLastname());
        accountLoginResponse.setRole(account.getRole());
        accountLoginResponse.setEmail(account.getEmail());
        accountLoginResponse.setCreateBy(account.getCreateBy());
        accountLoginResponse.setUpdateBy(account.getUpdateBy());
        accountLoginResponse.setDelFlag(account.getDelFlag());
        accountLoginResponse.setCreateDateTime(utilityTools.generateDatetimeMilliToString(account.getCreateDateTime()));
        accountLoginResponse.setUpdateDateTime(utilityTools.generateDatetimeMilliToString(account.getCreateDateTime()));
        accountLoginResponse.setToken(token);

        return accountLoginResponse;
    }
}
