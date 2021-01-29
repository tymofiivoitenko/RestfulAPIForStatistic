package com.demo.RestfulAPIForStatistic;

import com.demo.RestfulAPIForStatistic.dao.TransactionDao;
import com.demo.RestfulAPIForStatistic.model.Transaction;
import com.demo.RestfulAPIForStatistic.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionServiceTest {

    @Autowired
    protected TransactionService transactionService;

    @Autowired
    protected TransactionDao transactionDao;

    @Test
    public void isTransactionDateOld() {
        Transaction transaction = new Transaction(new BigDecimal(0), Instant.parse("2018-07-17T09:59:51.312Z"));
        assertTrue(transactionService.isTransactionDateOld(transaction));
    }

    @Test
    public void isTransactionDateInFuture() {
        Transaction transaction = new Transaction(new BigDecimal(0), Instant.now().plus(1, ChronoUnit.SECONDS));
        assertTrue(transactionService.isTransactionDateInFuture(transaction));
    }
    @Test
    public void isTransactionDateCorrect() {
        Transaction transaction = new Transaction(new BigDecimal(0), Instant.now());
        assertFalse(transactionService.isTransactionDateOld(transaction));
        assertFalse(transactionService.isTransactionDateInFuture(transaction));
    }

}
