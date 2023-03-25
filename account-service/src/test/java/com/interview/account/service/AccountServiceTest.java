package com.interview.account.service;

import com.interview.account.model.Account;
import com.interview.account.model.Customer;
import com.interview.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = { "spring.profiles.active=test" })
class AccountServiceTest {

    private AccountService accountService;
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void openAccountShouldCreateNewAccountWithInitialCredit() {

        var initialCredit = BigDecimal.valueOf(100);

        var customer = Customer.builder()
                .id(1L)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .firstName("John")
                .lastName("Doe")
                .build();

        var account = Account.builder()
                .id(1L)
                .customerId(customer.getId())
                .balance(initialCredit)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        when(accountRepository.save(any(Account.class))).thenReturn(account.getId());

        var accountId = accountService.openAccount(customer, initialCredit);

        assertEquals(account.getId(), accountId);
    }

    @Test
    void getAccountShouldReturnAccountIfItExists() {
        var accountId = 1L;
        var account = Account.builder()
                .id(accountId)
                .customerId(1L)
                .balance(BigDecimal.valueOf(100))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        when(accountRepository.findById(accountId)).thenReturn(account);

        var retrievedAccount = accountService.getAccount(accountId);

        verify(accountRepository).findById(accountId);
        assertEquals(account, retrievedAccount);
    }

    @Test
    void getAccountShouldReturnNullIfAccountDoesNotExist() {
        var accountId = 1L;

        when(accountRepository.findById(accountId)).thenReturn(null);

        var retrievedAccount = accountService.getAccount(accountId);

        verify(accountRepository).findById(accountId);
        assertNull(retrievedAccount);
    }
}
