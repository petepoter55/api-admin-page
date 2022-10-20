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
    public final static String ERROR_INQUIRY_DATA_NOT_FOUND = "Data Register is Not Found";
    public final static String ERROR_ACCOUNT_USERID_NULL = "User ID is Not Null";
    public final static String ERROR_ACCOUNT_USERID_SIZE = "User ID size must be between 1 and 5";
    public final static String ERROR_CREATE_IMAGE_ACCOUNT_DUPLICATE = "Data Image Account is Duplicate";
    public final static String ERROR_CREATE_IMAGE_ACCOUNT_NOT_FOUND = "Data Image Account is Not Found";
    public final static String ERROR_IMAGE_ACCOUNT_NOT_FOUND = "Data Image Account is Not Found";
    public final static String SUCCESS_REGISTER_ACCOUNT = "Register Account Success!";
    public final static String SUCCESS_UPDATE_ACCOUNT = "Update Account Success!";
    public final static String SUCCESS_DELETE_ACCOUNT = "Delete Account Success!";
    public final static String SUCCESS_INQUIRY_ACCOUNT = "Inquiry Account Success!";
    public final static String SUCCESS_CREATE_IMAGE_ACCOUNT = "Crete Image Account Success!";
    public final static String SUCCESS_GET_IMAGE_ACCOUNT = "Get Image Account Success!";
    public final static String SUCCESS_UPDATE_IMAGE_ACCOUNT = "Update Image Account Success!";

    //product
    public final static String SUCCESS_CREATE_PRODUCT = "Create Product Success!";
    public final static String SUCCESS_UPDATE_PRODUCT = "Update Product Success!";
    public final static String SUCCESS_DELETE_PRODUCT = "Delete Product Success!";
    public final static String SUCCESS_INQUIRY_PRODUCT = "Inquiry Product Success!";
    public final static String SUCCESS_CREATE_IMAGE_PRODUCT = "Crete Image Product Success!";
    public final static String SUCCESS_GET_IMAGE_PRODUCT = "Get Image Product Success!";
    public final static String SUCCESS_UPDATE_IMAGE_PRODUCT = "Update Image Product Success!";
    public final static String SUCCESS_IMPORT_PRODUCT = "Import Product Success!";
    public final static String ERROR_CREATE_PRODUCT_DUPLICATE = "Data Product is Duplicate";
    public final static String ERROR_CREATE_IMAGE_PRODUCT_DUPLICATE = "Data Image Product is Duplicate";
    public final static String ERROR_PRODUCT_NOT_FOUND = "Data Product is Not Found";
    public final static String ERROR_IMAGE_PRODUCT_NOT_FOUND = "Data Image Product is Not Found";
    public final static String ERROR_PRODUCT_CODE_NULL = "Product Code is Not Null";
    public final static String ERROR_PRODUCT_CODE_SIZE = "Product Code size must be between 1 and 50";
    public final static String PRODUCT_STATUS_ACTIVE = "active";
    public final static String PRODUCT_STATUS_INACTIVE = "Inactive";
    public final static String ERROR_FILE_TYPE_INVALID = "Invalid File Type Should be Excel file.";
    public final static String ERROR_FILE_IMAGE_TYPE_INVALID = "Invalid File Type Should be {jpeg|png} file.";
    public final static String ERROR_FILE_IMAGE_NULL = "Invalid File is not null.";

    public final static String TYPE_FILE_EXCEL = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public final static String TYPE_FILE_JPEG = "image/jpeg";
    public final static String TYPE_FILE_PNG = "image/png";
    public final static String[] TYPE_FILE_IMAGE = {TYPE_FILE_PNG,TYPE_FILE_JPEG};
    //log
    public final static String TYPE_REGISTER_SUCCESS = "R01";
    public final static String TYPE_UPDATE_SUCCESS = "R02";
    public final static String TYPE_REGISTER_FAILED = "F01";
    public final static String TYPE_UPDATE_FAILED = "F02";

    public final static String SUCCESS_INQUIRY_LOG = "Inquiry Log Account Success!";
    public final static String ERROR_INQUIRY_LOG_DATA_NOT_FOUND = "Data Log is Not Found";
}
