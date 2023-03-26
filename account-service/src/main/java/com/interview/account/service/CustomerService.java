package com.interview.account.service;

import com.interview.account.model.Customer;
import com.interview.account.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer(Long customerId) {
        log.info("Getting customer with ID {}", customerId);
        return customerRepository.findById(customerId);
    }
}
