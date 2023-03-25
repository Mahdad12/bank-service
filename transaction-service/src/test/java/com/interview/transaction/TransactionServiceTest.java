package com.interview.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.interview.transaction.dto.TransactionDto;
import com.interview.transaction.model.Transaction;
import com.interview.transaction.repository.TransactionRepository;
import com.interview.transaction.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = { "spring.profiles.active=test" })
public class TransactionServiceTest {

    private TransactionRepository transactionRepository;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    void testGetTransactionsByAccountId() {

        var accountId = 1L;
        List<Transaction> expectedTransactions = List.of(Transaction.builder()
                .amount(BigDecimal.valueOf(100))
                .accountId(accountId)
                .build());

        when(transactionRepository.findByAccountId(accountId)).thenReturn(expectedTransactions);
        List<TransactionDto> actualTransactions = transactionService.getTransactionsByAccount(accountId);

        verify(transactionRepository).findByAccountId(accountId);

        assert actualTransactions.size() == 1;

        assertEquals(expectedTransactions.get(0).getAmount().doubleValue(),
                actualTransactions.get(0).getAmount().doubleValue());

    }

    @Test
    void testCreateTransaction() {
        var account = 1L;
        var initialCredit = BigDecimal.valueOf(100);

        List<Transaction> expectedTransactions = List.of(Transaction.builder()
                .amount(BigDecimal.valueOf(100))
                .accountId(account)
                .build());

        transactionService.createTransaction(account, initialCredit);

        when(transactionRepository.findByAccountId(account)).thenReturn(expectedTransactions);
        var transactionsOfAccount = transactionRepository.findByAccountId(account);

        verify(transactionRepository).save(any(Transaction.class));
        assertEquals(transactionsOfAccount.get(0).getAmount().doubleValue(), initialCredit.doubleValue());
    }
}
