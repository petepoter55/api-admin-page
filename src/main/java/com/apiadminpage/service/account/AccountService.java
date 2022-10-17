package com.apiadminpage.service.account;

import com.apiadminpage.entity.account.Account;
import com.apiadminpage.entity.account.AccountInfo;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.request.account.AccountRequest;
import com.apiadminpage.model.request.account.AccountUpdateRequest;
import com.apiadminpage.model.request.account.InquiryAccountRequest;
import com.apiadminpage.model.request.log.LogRequest;
import com.apiadminpage.model.response.Response;
import com.apiadminpage.model.response.account.ResponseAccount;
import com.apiadminpage.model.response.account.ResponseAccountInfo;
import com.apiadminpage.repository.account.AccountInfoRepository;
import com.apiadminpage.repository.account.AccountRepository;
import com.apiadminpage.service.log.LogService;
import com.apiadminpage.utils.UtilityTools;
import com.apiadminpage.validator.ValidateAccount;
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
    private final AccountInfoRepository accountInfoRepository;
    private final AccountRepository accountRepository;
    private final ValidateAccount validateAccount;
    private final LogService logService;
    private final AccountInfoService accountInfoService;
    private final EntityManager entityManager;

    UtilityTools utilityTools = new UtilityTools();

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountInfoRepository accountInfoRepository, ValidateAccount validateAccount, LogService logService, AccountInfoService accountInfoService, EntityManager entityManager) {
        this.accountInfoRepository = accountInfoRepository;
        this.accountRepository = accountRepository;
        this.validateAccount = validateAccount;
        this.logService = logService;
        this.accountInfoService = accountInfoService;
        this.entityManager = entityManager;
    }

    @Transactional
    public Response createAccount(AccountRequest accountRequest) {
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
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | ParseException e) {
            status = false;
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        } finally {
            String type = Constant.TYPE_REGISTER_SUCCESS;
            if (!status) {
                type = Constant.TYPE_REGISTER_FAILED;
            }
            logService.insertLog(new LogRequest(accountRequest.getCreateBy(), type, currentDate));
        }

        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_REGISTER_ACCOUNT, account);
    }

    @Transactional
    public Response updateAccount(AccountUpdateRequest accountUpdateRequest) {
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
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            status = false;
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        } finally {
            String type = Constant.TYPE_UPDATE_SUCCESS;
            if (!status) {
                type = Constant.TYPE_UPDATE_FAILED;
            }
            logService.insertLog(new LogRequest(accountUpdateRequest.getUsername(), type, currentDate));
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_UPDATE_ACCOUNT, account);
    }

    public Response deleteAccount(Integer id) {
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
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_DELETE_ACCOUNT, null);
    }

    public Response inquiryAccountById(Integer id) {
        ResponseAccount responseAccount = new ResponseAccount();
        try {
            Optional<Account> accountOptional = accountRepository.findById(id);
            if (!accountOptional.isPresent()) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_INQUIRY_DATA_NOT_FOUND);
            }

            responseAccount = mapInquiryAccount(accountOptional.get());

        } catch (ResponseException e) {
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }

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
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }

        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_INQUIRY_ACCOUNT, responseAccountList);
    }

    public Response inquiryByPage(InquiryAccountRequest request) {
        List<Account> accountList;

        try {
            accountList = searchAccountByPage(request);
            if (accountList.size() < 0) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_INQUIRY_DATA_NOT_FOUND);
            }
        } catch (ResponseException e) {
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        }

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
            if (request.getCreateDateTimeEnd() != null && !("").equals(request.getCreateDateTimeEnd())){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(accountRoot.get("createDateTime"),request.getCreateDateTimeEnd()));
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

}
