package com.interview.account.repository;

import com.interview.account.model.Account;

public interface AccountRepository {

    Account findById(Long id);

    Long save(Account account);
}