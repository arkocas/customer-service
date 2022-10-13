package com.alirizakocas.customer.service.impl;

import com.alirizakocas.customer.model.AuthModel;
import com.alirizakocas.customer.model.CustomerRequestModel;
import com.alirizakocas.customer.entity.CustomerEntity;
import com.alirizakocas.customer.error.BadRequestException;
import com.alirizakocas.customer.jwt.TokenManager;
import com.alirizakocas.customer.repository.CustomerRepository;
import com.alirizakocas.customer.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerService implements ICustomerService {

    private AuthenticationManager authenticationManager;
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;
    private TokenManager tokenManager;

    @Override
    public String createCustomer(CustomerEntity customer) {
        validateCustomer(customer);

        boolean customerExist = customerRepository.findByUsername(customer.getUsername()).isPresent();
        if(customerExist) {
            throw new IllegalStateException("username already taken");
        }

        String password = customer.getPassword();
        saveCustomer(customer);
        log.info("::createCustomer customer created username:{}", customer.getUsername());

        AuthModel auth = new AuthModel(customer.getUsername(), password);
        return getToken(auth);
    }

    public CustomerEntity getCustomerByUsername(String username){
        if(!StringUtils.hasLength(username)) {
            throw new BadRequestException("username is not valid");
        }

        return customerRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Customer does not exist"));
    }

    @Override
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomerByUsername(String username) {
        if(!StringUtils.hasLength(username)) {
            throw new BadRequestException("username is not valid");
        }

        Optional<CustomerEntity> customerExist = customerRepository.findByUsername(username);

        if(customerExist.isPresent()) {
            CustomerEntity customer = customerExist.get();
            customerRepository.deleteById(customer.getId());
        } else {
            throw new IllegalStateException("username does not exist");
        }
    }

    @Override
    public void updateCustomer(CustomerRequestModel customerRequestModel) {
        CustomerEntity existCustomer = customerRepository.findByUsername(customerRequestModel.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Customer does not exist"));

        //  name ve lastname null olamaz
        if(customerRequestModel.getLastname() != null) {
            existCustomer.setLastname(customerRequestModel.getLastname());
        }
        if(customerRequestModel.getName() != null) {
            existCustomer.setName(customerRequestModel.getName());
        }

        existCustomer.setContactNumber(customerRequestModel.getContactNumber());
        existCustomer.setActive(customerRequestModel.isActive());
        existCustomer.setLastUpdateDate(LocalDateTime.now());

        customerRepository.save(existCustomer).getId();
    }

    public String getToken(AuthModel authModel) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authModel.getUsername(), authModel.getPassword()));
            if(auth.isAuthenticated()) {
                return  "{\"token\":\"" +
                        tokenManager.generateToken(authModel.getUsername()) +
                        "\"}";
            } else {
                return "Auth not approved";
            }
        } catch (Exception e){
            log.error("::getToken token create error username: {}", authModel.getUsername());
            throw new IllegalStateException("An error occurred while creating token");
        }
    }

    public void saveCustomer(CustomerEntity customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setCreateDate(LocalDateTime.now());
        customerRepository.save(customer);
    }

    public void validateCustomer(CustomerEntity customer) {
        if(!StringUtils.hasLength(customer.getUsername()) || !StringUtils.hasLength(customer.getPassword())
                || StringUtils.containsWhitespace(customer.getUsername()) || StringUtils.containsWhitespace(customer.getPassword())) {

            throw new BadRequestException("username or password is not valid");
        }
    }
}
