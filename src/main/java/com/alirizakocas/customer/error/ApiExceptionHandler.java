package com.alirizakocas.customer.error;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbiddenException(ForbiddenException exception, HttpServletRequest request){
        ApiError error = new ApiError(403, exception.getMessage(), request.getServletPath(), new Date());
        return error;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException exception, HttpServletRequest request){
        ApiError error = new ApiError(404, exception.getMessage(), request.getServletPath(), new Date());
        return error;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleUsernameNotFoundException(UsernameNotFoundException exception, HttpServletRequest request){
        ApiError error = new ApiError(404, exception.getMessage(), request.getServletPath(), new Date());
        return error;
    }
}