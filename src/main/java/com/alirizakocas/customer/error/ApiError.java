package com.alirizakocas.customer.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//Sadece null değer içermeyenleri jsona ekle
@RequiredArgsConstructor
public class ApiError {
    @NonNull
    private int status;
    @NonNull
    private String message;
    @NonNull
    private String path;
    @NonNull
    private Date timestamp;

    private Map<String, String> errors;
}
