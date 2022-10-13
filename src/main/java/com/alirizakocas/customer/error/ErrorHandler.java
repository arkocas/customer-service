package com.alirizakocas.customer.error;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//default error pathi atadık
//Springin Error kontrolleri yerine uygulamamıza özgü bir error controller yazdık.
//Error handler sayesinde hata mesajlarının ApiError tipinde olacağını kesinleştirdik
@RestController
@RequiredArgsConstructor
public class ErrorHandler implements ErrorController {

    @Autowired
    private final ErrorAttributes errorAttributes;

    //gelen requestdeki hata mesajlarının tümü /errora düşer
    //validation mesajlarının ve hata mesajlarının döndürüldüğü genel bir error controller implemente ettik
    @RequestMapping("/error")
    public ApiError handleError(WebRequest webRequest) {
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.BINDING_ERRORS));//hata sonucu dönen attribute'leri aldık
        String message = (String) attributes.get("message");
        String path = (String) attributes.get("path");
        int status = (Integer) attributes.get("status");
        Date timestamp = (Date) attributes.get("timestamp");
        ApiError error = new ApiError(status, message, path, timestamp);
        if (attributes.containsKey("errors")) {
            List<FieldError> fieldErrors = (List<FieldError>) attributes.get("errors");
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : fieldErrors) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            error.setErrors(errors);
        }
        return error;
    }
}
