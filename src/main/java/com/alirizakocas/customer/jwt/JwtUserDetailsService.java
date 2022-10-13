package com.alirizakocas.customer.jwt;


import com.alirizakocas.customer.entity.CustomerEntity;
import com.alirizakocas.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerEntity customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Customer does not exist"));

        return new User(customer.getUsername(), customer.getPassword(), new ArrayList<>());
    }
}
