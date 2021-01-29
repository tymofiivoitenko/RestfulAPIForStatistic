package com.demo.RestfulAPIForStatistic.dao;

import com.demo.RestfulAPIForStatistic.model.Transaction;

import java.util.List;

/**
 * @author tymofiivoitenko
 */
public interface TransactionDao {

    List<Transaction> getTransactions();
    void add(Transaction transaction);
    void deleteAll();
}
