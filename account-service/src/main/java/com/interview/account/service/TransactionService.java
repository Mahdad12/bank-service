package com.interview.account.service;

import com.interview.account.api.TransactionClient;
import com.interview.account.dto.CreateTransactionRequest;
import com.interview.account.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class TransactionService {

    private final TransactionClient transactionClient;

    public TransactionService(TransactionClient transactionClient) {
        this.transactionClient = transactionClient;
    }

    public List<TransactionDto> getTransactionsByAccountId(Long accountId) {
        return transactionClient.getTransactionsByAccountId(accountId);
    }

    public Long createTransaction(Long account, BigDecimal initialCredit) {
        CreateTransactionRequest createTransactionRequest =
                CreateTransactionRequest.builder()
                        .accountId(account)
                        .amount(initialCredit)
                        .build();

        return transactionClient.createTransaction(createTransactionRequest);
    }
}
