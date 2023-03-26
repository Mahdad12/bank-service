package com.interview.account.controller;

import com.interview.account.dto.AccountResponse;
import com.interview.account.dto.OpenAccountRequest;
import com.interview.account.dto.OpenAccountResponse;
import com.interview.account.dto.TransactionDto;
import com.interview.account.model.Account;
import com.interview.account.model.Customer;
import com.interview.account.service.AccountService;
import com.interview.account.service.CustomerService;
import com.interview.account.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/account")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    public AccountController(AccountService accountService,
                             CustomerService customerService,
                             TransactionService transactionService) {
        this.accountService = accountService;
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    @Operation(summary = "Create an account, if initial credit is bigger than zero create a transaction too")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @ApiResponse(responseCode = "200", description = "Account created successfully")
    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OpenAccountResponse> openAccount(@RequestBody OpenAccountRequest request) {
        Customer customer = customerService.getCustomer(request.getCustomerId());
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        Long accountId = accountService.openAccount(customer, request.getInitialCredit());
        if (request.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
            transactionService.createTransaction(accountId, request.getInitialCredit());
        }
        return ResponseEntity.ok().body(OpenAccountResponse.builder()
                .accountId(accountId)
                .build());
    }

    @Operation(summary = "get an account detail with transactions")
    @ApiResponse(responseCode = "404", description = "Account not found")
    @ApiResponse(responseCode = "200", description = "Response account detail with transactions")
    @GetMapping(value = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResponse> getAccount(@NonNull @PathVariable Long accountId) {

        Account account = accountService.getAccount(accountId);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        Customer customer = customerService.getCustomer(account.getCustomerId());

        List<TransactionDto> transactions = transactionService.getTransactionsByAccountId(accountId);
        AccountResponse accountResponse = AccountResponse.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .balance(account.getBalance())
                .transactions(transactions)
                .build();

        return ResponseEntity.ok(accountResponse);
    }
}
