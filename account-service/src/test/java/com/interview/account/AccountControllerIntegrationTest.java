package com.interview.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.account.dto.OpenAccountRequest;
import com.interview.account.dto.TransactionDto;
import com.interview.account.model.Account;
import com.interview.account.model.Customer;
import com.interview.account.service.AccountService;
import com.interview.account.service.CustomerService;
import com.interview.account.service.TransactionService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(properties = { "spring.profiles.active=test" })
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private TransactionService transactionService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldOpenAccount() throws Exception {
        // Given
        var accountId = 1L;
        var openAccountRequest = OpenAccountRequest.builder()
                .customerId(1L)
                .initialCredit(BigDecimal.valueOf(1000))
                .build();

        // When
        when(customerService.getCustomer(1L))
                .thenReturn(Customer.builder()
                        .id(1L)
                        .firstName("John")
                        .lastName("Doe")
                        .build());


        when(accountService.openAccount(any(Customer.class), any(BigDecimal.class)))
                .thenReturn(accountId);

        when(transactionService.createTransaction(anyLong(), any(BigDecimal.class))).
                thenReturn(1L);

        ResultActions resultActions = mockMvc.perform(post("/v1/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(openAccountRequest)));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.accountId").value(accountId));
    }

    @Test
    public void testOpenAccountWhenAccountIsNull() throws Exception {
        var request = new OpenAccountRequest();
        request.setCustomerId(9999L);
        request.setInitialCredit(BigDecimal.TEN);

        when(customerService.getCustomer(request.getCustomerId())).thenReturn(null);

        mockMvc.perform(post("/v1/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verify(accountService, never()).openAccount(any(Customer.class), any(BigDecimal.class));
        verify(transactionService, never()).createTransaction(anyLong(), any(BigDecimal.class));
    }

    @Test
    public void shouldGetAccount() throws Exception {
        var accountId = 1L;

        var account =  Account.builder()
                .id(accountId)
                .customerId(1L)
                .balance(BigDecimal.valueOf(1000))
                .build();

        var customer = Customer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        List<TransactionDto> transactions =
                Collections.singletonList(TransactionDto.builder()
                        .id(accountId)
                        .amount(BigDecimal.valueOf(500))
                        .createdAt(Instant.now())
                        .build());

        when(accountService.getAccount(accountId))
                .thenReturn(account);

        when(customerService.getCustomer(account.getCustomerId()))
                .thenReturn(customer);

        when(transactionService.getTransactionsByAccountId(accountId))
                .thenReturn(transactions);

        ResultActions resultActions = mockMvc.perform(get("/v1/api/account/{accountId}", accountId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
                .andExpect(jsonPath("$.balance").value(account.getBalance()))
                .andExpect(jsonPath("$.transactions[0].amount").value(transactions.get(0).getAmount()))
                .andExpect(jsonPath("$.transactions[0].createdAt").isNotEmpty());
    }

    @Test
    public void testGetAccountWhenAccountIsNull() throws Exception {
        when(accountService.getAccount(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/account/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(customerService, never()).getCustomer(anyLong());
        verify(transactionService, never()).getTransactionsByAccountId(anyLong());
    }

}
