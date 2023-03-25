package com.interview.account.service;

import com.interview.account.model.Account;
import com.interview.account.model.Customer;
import com.interview.account.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Long openAccount(Customer customer, BigDecimal initialCredit) {
        log.info("Opening account for customer {} with initial credit {}", customer.getId(), initialCredit);
        Account account = Account.builder()
                .customerId(customer.getId())
                .balance(initialCredit)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        Long savedAccountId = accountRepository.save(account);
        log.info("Account created successfully for customer {} with initial credit {}", customer.getId(), initialCredit);
        return savedAccountId;
    }

    public Account getAccount(Long accountId) {
        log.info("Getting account with ID {}", accountId);
        return accountRepository.findById(accountId);
    }
}
