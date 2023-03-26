package com.interview.account.api;


import com.interview.account.dto.CreateTransactionRequest;
import com.interview.account.dto.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "transactionClient", url = "${client.transaction.baseUrl}")
public interface TransactionClient {

    @RequestMapping(method = RequestMethod.GET,
            value ="/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<TransactionDto> getTransactionsByAccountId(@PathVariable("accountId") long accountId);

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Long createTransaction(@RequestBody CreateTransactionRequest request);

}
