package com.interview.account.service;

import com.interview.account.api.TransactionClient;
import com.interview.account.dto.CreateTransactionRequest;
import com.interview.account.dto.TransactionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = { "spring.profiles.active=test" })
public class TransactionServiceTest {

    private TransactionClient transactionClient;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionClient = mock(TransactionClient.class);
        transactionService = new TransactionService(transactionClient);
    }

    @Test
    void testGetTransactionsByAccountId() {

        var accountId = 1L;
        List<TransactionDto> expectedTransactions = List.of(TransactionDto.builder()
                .amount(BigDecimal.valueOf(100))
                .build());

        when(transactionClient.getTransactionsByAccountId(accountId)).thenReturn(expectedTransactions);
        List<TransactionDto> actualTransactions = transactionService.getTransactionsByAccountId(accountId);

        verify(transactionClient).getTransactionsByAccountId(accountId);
        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void testCreateTransaction() {

        var account = 1L;
        var initialCredit = BigDecimal.valueOf(100);
        var expectedRequest = CreateTransactionRequest.builder()
                .accountId(account)
                .amount(initialCredit)
                .build();

        transactionService.createTransaction(account, initialCredit);

        verify(transactionClient).createTransaction(expectedRequest);
    }
}
