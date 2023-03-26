package com.interview.account.repository;

import com.interview.account.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = { "spring.profiles.active=test" })
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testSaveAndFindById() {
        var id = 4L;
        var expectedAccount = Account.builder()
                .id(id)
                .customerId(1L)
                .balance(BigDecimal.valueOf(200.00))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        var accountId = accountRepository.save(expectedAccount);


        var actualAccount = accountRepository.findById(accountId);


        assertNotNull(actualAccount);
        assertEquals(expectedAccount.getId(), actualAccount.getId());
        assertEquals(expectedAccount.getCustomerId(), actualAccount.getCustomerId());
        assertEquals(expectedAccount.getBalance().doubleValue(), actualAccount.getBalance().doubleValue());
        assertNotNull(actualAccount.getCreatedAt());
        assertNotNull(actualAccount.getUpdatedAt());
    }

}
