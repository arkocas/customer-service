package com.alirizakocas.customer.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// ResponseStatus belirtmezsek ErrorHandler'a 404 hatası olarak değil, 500 hatası olarak gidiyor.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    public NotFoundException(String message){
        super(message);
    }
}
