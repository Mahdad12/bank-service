package com.interview.transaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.interview.transaction.dto.CreateTransactionRequest;
import com.interview.transaction.dto.TransactionDto;
import com.interview.transaction.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/v1/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Create transaction")
    @ApiResponse(responseCode = "200", description = "Transactions created successfully")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createTransaction(@RequestBody CreateTransactionRequest request) {
        var transactionId = transactionService.createTransaction(request.getAccountId(), request.getAmount());
        return ResponseEntity.ok().body(transactionId);
    }



    @Operation(summary = "get transactions")
    @ApiResponse(responseCode = "200", description = "Returns transactions of an account")
    @GetMapping(value = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransactionDto>> getTransactionsByAccountId(@NonNull @PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getTransactionsByAccount(accountId));
    }


}
