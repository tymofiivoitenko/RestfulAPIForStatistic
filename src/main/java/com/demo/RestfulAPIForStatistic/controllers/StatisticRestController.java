package com.demo.RestfulAPIForStatistic.controllers;

import com.demo.RestfulAPIForStatistic.model.Statistics;
import com.demo.RestfulAPIForStatistic.model.Transaction;
import com.demo.RestfulAPIForStatistic.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tymofiivoitenko
 */
@RestController
public class StatisticRestController {

    @Autowired
    private TransactionService transactionService;

    // Validate and create transaction
    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<String> postTransaction(@RequestBody Transaction transaction) {
        // Check if transaction date is in the future
        // Return empty body with status code 422
        if (transactionService.isTransactionDateInFuture(transaction)) {
            return new ResponseEntity<>("", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Check if transaction is older than 60 seconds
        // Return empty body with status code 204
        if (transactionService.isTransactionDateOld(transaction)) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }

        // Create transaction, return status code 201
        transactionService.create(transaction);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    // Returns the statistics based on the transactions that happened in the last 60 seconds
    @RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Statistics getStatistics() {
        return transactionService.getStatistics();
    }

    // Delete all existing transactions
    @RequestMapping(value = "/transactions", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAll() {
        transactionService.deleteAll();
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }
}
