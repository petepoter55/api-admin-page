package com.apiadminpage.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String exceptionCode;
    private Object data;
    private String description;
    private String moreInfo;

    public ResponseException() {
    }

    public ResponseException(String exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public ResponseException(String exceptionCode, String message, Throwable t) {
        super(message, t);
        this.exceptionCode = exceptionCode;
    }

    public ResponseException(String exceptionCode, String message, Object data) {
        super(message);
        this.exceptionCode = exceptionCode;
        this.data = data;
    }

    public ResponseException(String exceptionCode, String message, Object data, String description, String moreInfo) {
        super(message);
        this.exceptionCode = exceptionCode;
        this.data = data;
        this.description = description;
        this.moreInfo = moreInfo;
    }
}
