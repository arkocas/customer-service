package com.alirizakocas.customer.controller;

import com.alirizakocas.customer.model.CustomerRequestModel;
import com.alirizakocas.customer.model.CustomerResponseModel;
import com.alirizakocas.customer.entity.CustomerEntity;
import com.alirizakocas.customer.service.impl.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/customer")
@Api(value = "CustomerController documentation")
public class CustomerController {
    private CustomerService customerService;
    private ModelMapper modelMapper;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "New customer create method")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerEntity customer){
        String token = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("/{username}")
    @ApiOperation(value = "Get customer info method")
    public CustomerResponseModel getCustomerInfo(@PathVariable String username){
        return convertToDto(customerService.getCustomerByUsername(username));
    }

    @GetMapping()
    @ResponseBody
    @ApiOperation(value = "Get all customer method")
    public List<CustomerResponseModel> getAllCustomers(){
        List<CustomerEntity> customers = customerService.getAllCustomers();
        return customers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Customer update method")
    public void updateCustomer(@Valid @RequestBody CustomerRequestModel requestModel){
        customerService.updateCustomer(requestModel);
    }

    @DeleteMapping("{username}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Customer delete method")
    public void deleteCustomer(@PathVariable String username){
        customerService.deleteCustomerByUsername(username);
    }

    private CustomerResponseModel convertToDto(CustomerEntity entity) {
        CustomerResponseModel customer = modelMapper.map(entity, CustomerResponseModel.class);
        return customer;
    }
}
