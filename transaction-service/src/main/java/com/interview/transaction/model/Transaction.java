package com.interview.transaction.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private Long id; // best practice would be using UUID but H2 does not support it
    private Long accountId;
    private BigDecimal amount;
    private Instant createdAt;
    private Instant updatedAt;

}
