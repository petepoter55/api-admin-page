package com.apiadminpage.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private Boolean status;
    private String code;
    private String message;
    private T data;

    public Response(Boolean status, String code, String message, T data) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> success(T data) {
        return new Response<T>(true, null, null, data);
    }

    public static <T> Response<T> success(String code, String message, T data) {
        return new Response<T>(true, code, message, data);
    }

    public static <T> Response<T> fail(String code, String message) {
        return new Response<T>(false, code, message, null);
    }

    public static <T> Response<T> fail(String code, String message, T data) {
        return new Response<T>(false, code, message, data);
    }

    public static <T> Response<T> error(String code, String message) {
        return new Response<T>(false, code, message, null);
    }

    public static <T> Response<T> error(String code, String message, T data) {
        return new Response<T>(false, code, message, data);
    }


}
