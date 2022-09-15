package com.apiadminpage.exception.customError;


import com.apiadminpage.exception.ResponseException;
import org.apache.velocity.exception.ResourceNotFoundException;

@FunctionalInterface
public interface ExceptionHandler<T> {
    public void handler(T response)
            throws ResourceNotFoundException, ResponseException;
}
