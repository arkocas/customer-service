package com.alirizakocas.customer.controller;

import com.alirizakocas.customer.config.ModelMapperConfig;
import com.alirizakocas.customer.entity.CustomerEntity;
import com.alirizakocas.customer.model.CustomerResponseModel;
import com.alirizakocas.customer.service.impl.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes= {ModelMapperConfig.class})
public class CustomerControllerTest {
    @InjectMocks
    CustomerController customerController;
    @Mock
    CustomerService customerService;

    @Spy
    private ModelMapper modelMapper;

    CustomerEntity customerEntity;

    private final String username = "alirizakocas";

    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        customerEntity = new CustomerEntity();
        customerEntity.setUsername(username);
        customerEntity.setName("Ali");
        customerEntity.setLastname("Kocas");
        customerEntity.setContactNumber("99999");
        customerEntity.setActive(false);

    }

    @Test
    void testGetCustomer()
    {
        when(customerService.getCustomerByUsername(anyString())).thenReturn(customerEntity);
        CustomerResponseModel customerInfo = customerController.getCustomerInfo(username);
        assertNotNull(customerInfo);
        assertEquals(customerEntity.getName(), customerInfo.getName());
        assertEquals(customerEntity.getLastname(), customerInfo.getLastname());
        assertEquals(customerEntity.isActive(), customerInfo.isActive());
    }
}
