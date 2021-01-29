package com.demo.RestfulAPIForStatistic.service;

import com.demo.RestfulAPIForStatistic.model.Statistics;
import com.demo.RestfulAPIForStatistic.model.Transaction;

/**
 * @author tymofiivoitenko
 */
public interface TransactionService {

    void create(Transaction transaction);

    Statistics getStatistics();

    void deleteAll();

    boolean isTransactionDateInFuture(Transaction transaction);

    boolean isTransactionDateOld(Transaction transaction);
}
