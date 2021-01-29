package com.demo.RestfulAPIForStatistic.service;

import com.demo.RestfulAPIForStatistic.model.Statistics;
import com.demo.RestfulAPIForStatistic.model.Transaction;

import java.util.List;

/**
 * @author tymofiivoitenko
 */
public interface StatisticsService {
    Statistics getStatistics();
    void updateStatistics();
    void createStatistics(List<Transaction> transactions);
}
