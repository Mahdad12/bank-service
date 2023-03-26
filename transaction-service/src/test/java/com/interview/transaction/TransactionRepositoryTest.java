package com.interview.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.interview.transaction.model.Transaction;
import com.interview.transaction.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = { "spring.profiles.active=test" })
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void testSaveAndFindById() {
        var id = 1L;
        var accountId = 1L;
        var transaction = Transaction.builder()
                .id(id)
                .accountId(1L)
                .amount(BigDecimal.valueOf(200.0))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        var transactionId = transactionRepository.save(transaction);


        var transactions = transactionRepository.findByAccountId(accountId);


        assert !transactions.isEmpty();
        assertNotNull(transactionId);
        assertEquals(transactions.get(3).getAmount().doubleValue(), transaction.getAmount().doubleValue());
        assertNotNull(transactions.get(3).getCreatedAt());
        assertNotNull(transactions.get(3).getUpdatedAt());
    }

}
