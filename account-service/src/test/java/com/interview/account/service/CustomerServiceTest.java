package com.interview.account.service;

import com.interview.account.model.Customer;
import com.interview.account.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = { "spring.profiles.active=test" })
public class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void testGetCustomer() {
        var customerId = 1L;
        var expectedCustomer = Customer.builder()
                .id(customerId)
                .firstName("John")
                .lastName("Doe")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        when(customerRepository.findById(customerId)).thenReturn(expectedCustomer);

        var actualCustomer = customerService.getCustomer(customerId);

        verify(customerRepository).findById(customerId);
        assertEquals(expectedCustomer, actualCustomer);
    }
}
