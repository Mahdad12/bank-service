package com.interview.transaction.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.interview.transaction.dto.TransactionDto;
import com.interview.transaction.model.Transaction;
import com.interview.transaction.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Long createTransaction(Long accountId, BigDecimal amount) {
        log.info("Creating transaction of {} for account with ID {}", amount, accountId);

        var transaction = Transaction.builder()
                .accountId(accountId)
                .amount(amount)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        var transactionId = transactionRepository.save(transaction);
        log.info("Transaction created for account with ID {} and transaction ID: {}", accountId, transactionId);

        return transactionId;
    }

    public List<TransactionDto> getTransactionsByAccount(Long accountId) {
        log.info("Getting transactions for account with ID {}", accountId);
        return transactionRepository.findByAccountId(accountId).stream().map(this::convertToDto).toList();
    }

    public TransactionDto convertToDto(Transaction transaction) {
        return TransactionDto.builder()
                .amount(transaction.getAmount())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
