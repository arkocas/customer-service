package com.alirizakocas.customer.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerResponseModel {
    private Integer id;
    private String username;
    private String name;
    private String lastname;
    private String contactNumber;
    private boolean active;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}
