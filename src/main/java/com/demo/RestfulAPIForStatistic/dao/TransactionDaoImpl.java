package com.demo.RestfulAPIForStatistic.dao;

import com.demo.RestfulAPIForStatistic.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author tymofiivoitenko
 */
@Repository
public class TransactionDaoImpl implements TransactionDao {

    private List<Transaction> listOfAllTransactions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public List<Transaction> getTransactions() {
        return listOfAllTransactions;
    }

    @Override
    public void add(Transaction transaction) {
        listOfAllTransactions.add(transaction);
    }

    @Override
    public void deleteAll() {
        listOfAllTransactions.clear();
    }

}
