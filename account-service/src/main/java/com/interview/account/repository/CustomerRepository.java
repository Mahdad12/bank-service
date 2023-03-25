package com.interview.account.repository;

import com.interview.account.model.Customer;


public interface CustomerRepository {

    Customer findById(Long id);

    Long save(Customer customer);
}
