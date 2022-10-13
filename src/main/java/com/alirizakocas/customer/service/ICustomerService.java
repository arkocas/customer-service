package com.alirizakocas.customer.service;

import com.alirizakocas.customer.model.CustomerRequestModel;
import com.alirizakocas.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface ICustomerService {
    @Transactional
    String createCustomer(CustomerEntity customer);

    CustomerEntity getCustomerByUsername(String username);

    List<CustomerEntity> getAllCustomers();

    void deleteCustomerByUsername(String username);

    @Transactional
    @Modifying
    void updateCustomer(CustomerRequestModel req);
}
