package com.interview.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.transaction.dto.CreateTransactionRequest;
import com.interview.transaction.dto.TransactionDto;
import com.interview.transaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(properties = { "spring.profiles.active=test" })
public class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldGetTransactionsOfAccount() throws Exception {

        var accountId = 1L;

        List<TransactionDto> transactions =
                Collections.singletonList(TransactionDto.builder()
                        .amount(BigDecimal.valueOf(500))
                        .createdAt(Instant.now())
                        .build());

        when(transactionService.getTransactionsByAccount(accountId))
                .thenReturn(transactions);

        ResultActions resultActions = mockMvc.perform(get("/v1/api/transaction/{accountId}", accountId)
                .contentType(MediaType.APPLICATION_JSON));

        var transaction = transactions.get(0);

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].amount").value(transaction.getAmount()))
                .andExpect(jsonPath("$.[0].createdAt").isNotEmpty());
    }

    @Test
    public void shouldCreateTransaction() throws Exception {
        // Given
        var accountId = 1L;

        var request = CreateTransactionRequest.builder()
                .accountId(accountId)
                .amount(BigDecimal.valueOf(1000))
                .build();

        when(transactionService.createTransaction(request.getAccountId(), request.getAmount())).thenReturn(1L);

        ResultActions resultActions = mockMvc.perform(post("/v1/api/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
