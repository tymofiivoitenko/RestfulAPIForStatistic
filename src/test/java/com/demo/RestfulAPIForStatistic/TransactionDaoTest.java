package com.demo.RestfulAPIForStatistic;

import com.demo.RestfulAPIForStatistic.dao.TransactionDao;
import com.demo.RestfulAPIForStatistic.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransactionDaoTest {

    @Autowired
    protected TransactionDao transactionDao;

    @Test
    public void areTransactionsSuccessfullyDeleted() {
        transactionDao.add(new Transaction(new BigDecimal(12.3343), Instant.now()));
        transactionDao.add(new Transaction(new BigDecimal(0), Instant.now()));
        transactionDao.deleteAll();

        List<Transaction> transactionList = transactionDao.getTransactions();
        assert (transactionList.size() == 0);
    }

    @Test
    public void isCorrectTransactionSavedSuccessfully() {
        transactionDao.add(new Transaction(new BigDecimal(12.3343), Instant.now()));
        transactionDao.add(new Transaction(new BigDecimal(0), Instant.now()));

        List<Transaction> transactionList = transactionDao.getTransactions();
        assert (transactionList.size() == 2);
    }

}
