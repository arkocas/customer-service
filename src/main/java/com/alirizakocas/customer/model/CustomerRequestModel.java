package com.alirizakocas.customer.model;

import lombok.Data;

@Data
public class CustomerRequestModel {
    private String username;
    private String name;
    private String lastname;
    private String contactNumber;
    private boolean active;
}
