package com.demo.RestfulAPIForStatistic.service;

import com.demo.RestfulAPIForStatistic.dao.TransactionDao;
import com.demo.RestfulAPIForStatistic.model.Statistics;
import com.demo.RestfulAPIForStatistic.model.Transaction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tymofiivoitenko
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final TransactionDao transactionDao;
    private Statistics statistics;

    public StatisticsServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    // Update statistics every 1 millisecond
    // Updating happens every millisecond because it's minimal timeStep between transaction dates
    // Transaction date format: YYYY-MM-DDThh:mm:ss.sssZ
    // When Statistic updater works in the background, GET request receive the already created Statistics
    // So, GET() time complexity is O(1);
    @Scheduled(fixedDelay = 1)
    public void updateStatistics() {
        // We copy arrayList in order to don't lock the original one
        List<Transaction> transactionList = new ArrayList<>(transactionDao.getTransactions());

        Instant instantMinuteAgo = Instant.now().minus(1, ChronoUnit.MINUTES);
        // We work only with new transactions, which are no older than 60 sec
        transactionList.stream()
                .filter(transaction -> instantMinuteAgo.compareTo(transaction.getTimestamp()) < 0)
                .collect(Collectors.toList());

        createStatistics(transactionList);
    }

    // Return Statistics
    @Override
    public Statistics getStatistics() {
        return statistics;
    }

    @Override
    public void createStatistics(List<Transaction> transactions) {
        long count = transactions.size();

        if (count == 0) {
            statistics = new Statistics(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0);
            return;
        }

        BigDecimal sum = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal max = transactions.stream()
                .max(Comparator.comparing(Transaction::getAmount))
                .map(Transaction::getAmount)
                .orElse(BigDecimal.ZERO);

        BigDecimal min = transactions.stream()
                .min(Comparator.comparing(Transaction::getAmount))
                .map(Transaction::getAmount)
                .orElse(BigDecimal.ZERO);

        BigDecimal average = sum.divide(BigDecimal.valueOf(count), BigDecimal.ROUND_HALF_UP);

        statistics = new Statistics(sum, average, max, min, count);
    }
}
