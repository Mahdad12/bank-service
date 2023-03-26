package com.interview.transaction.repository;

import com.interview.transaction.model.Transaction;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> findByAccountId(Long accountId);
    Long save(Transaction transaction);
}