package com.demo.RestfulAPIForStatistic.service;

import com.demo.RestfulAPIForStatistic.dao.TransactionDao;
import com.demo.RestfulAPIForStatistic.model.Statistics;
import com.demo.RestfulAPIForStatistic.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author tymofiivoitenko
 */
@Service
public class TransactionServiceIml implements TransactionService {

    private final TransactionDao transactionDao;
    private final StatisticsServiceImpl statisticsService;

    public TransactionServiceIml(TransactionDao transactionDao, StatisticsServiceImpl statisticsService) {
        this.transactionDao = transactionDao;
        this.statisticsService = statisticsService;
    }

    @Override
    public void create(Transaction transaction) {
        transactionDao.add(transaction);
    }

    // Get statistics for last 60 sec
    @Override
    public Statistics getStatistics() {
        return statisticsService.getStatistics();
    }

    // Delete all transactions
    @Override
    public void deleteAll() {
        transactionDao.deleteAll();
    }

    // Return true, if transaction date is greater than now();
    @Override
    public boolean isTransactionDateInFuture(Transaction transaction) {
        return transaction.getTimestamp().compareTo(Instant.now()) > 0;
    }

    // Return true, if transaction is older than 60 seconds;
    @Override
    public boolean isTransactionDateOld(Transaction transaction) {
        Instant instantMinuteAgo = Instant.now().minus(1, ChronoUnit.MINUTES);
        return instantMinuteAgo.compareTo(transaction.getTimestamp()) > 0;
    }
}
