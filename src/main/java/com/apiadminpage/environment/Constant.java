package com.apiadminpage.environment;

public class Constant {
    public final static String STATUS_CODE_SUCCESS = "200";
    public final static String STATUS_CODE_FAIL = "500";
    public final static String STATUS_CODE_ERROR = "400";
    public final static String STATUS_CODE_FOUND = "404";

    public final static Boolean STATUS_SUCCESS = true;
    public final static Boolean STATUS_FALSE = false;

    public final static String SUCCESS = "SUCCESS";

    //throw exception
    public final static String THROW_EXCEPTION = "Error : %s";

    //account request validator
    public final static String ERROR_ACCOUNT_REQUEST_EMAIL_INVALID = "Email or EmailConfirm invalid";
    public final static String ERROR_ACCOUNT_REQUEST_PASSWORD_INVALID = "Password or PasswordConfirm invalid";

    //register
    public final static String ERROR_REGISTER_CHECKDATA_DUPLICATE = "Data Register is Duplicate";
    public final static String ERROR_UPDATE_DATA_NOT_FOUND = "Data Register is Not Found";
    public final static String SUCCESS_REGISTER_ACCOUNT = "Register Account Success!";
    public final static String SUCCESS_UPDATE_ACCOUNT = "Update Account Success!";
    public final static String SUCCESS_DELETE_ACCOUNT = "Delete Account Success!";
    public final static String SUCCESS_INQUIRY_ACCOUNT = "Inquiry Account Success!";

    //log
    public final static String TYPE_REGISTER_SUCCESS = "R01";
    public final static String TYPE_UPDATE_SUCCESS = "R02";
    public final static String TYPE_REGISTER_FAILED = "F01";
    public final static String TYPE_UPDATE_FAILED = "F02";
}
