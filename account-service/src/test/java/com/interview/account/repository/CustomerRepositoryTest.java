package com.interview.account.repository;

import com.interview.account.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = { "spring.profiles.active=test" })
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testSaveAndFindById() {
        var id = 5L;

        var customer = Customer.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        var savedAccountId = customerRepository.save(customer);

        var savedCustomer = customerRepository.findById(savedAccountId);

        assertNotNull(savedCustomer);
        assertEquals(savedAccountId, savedCustomer.getId());
        assertEquals(customer.getFirstName(), savedCustomer.getFirstName());
        assertEquals(customer.getLastName(), savedCustomer.getLastName());
        assertNotNull(savedCustomer.getCreatedAt());
        assertNotNull(savedCustomer.getUpdatedAt());
    }
}