package com.demo.RestfulAPIForStatistic;

import com.demo.RestfulAPIForStatistic.dao.TransactionDao;
import com.demo.RestfulAPIForStatistic.model.Statistics;
import com.demo.RestfulAPIForStatistic.model.Transaction;
import com.demo.RestfulAPIForStatistic.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsServiceTest {

    @Autowired
    protected StatisticsService statisticsService;

    @Autowired
    protected TransactionDao transactionDao;

    @Test
    public void isStatisticsUpdatedSuccessfully() {
        transactionDao.deleteAll();
        transactionDao.add(new Transaction(new BigDecimal(12.3343), Instant.now()));
        transactionDao.add(new Transaction(new BigDecimal(1), Instant.now()));

        statisticsService.updateStatistics();

        Statistics testStatistics = new Statistics();
        testStatistics.setSum(new BigDecimal(13.33).setScale(2, BigDecimal.ROUND_HALF_UP));
        testStatistics.setAverage(new BigDecimal(6.67).setScale(2, BigDecimal.ROUND_HALF_UP));
        testStatistics.setMax(new BigDecimal(12.33).setScale(2, BigDecimal.ROUND_HALF_UP));
        testStatistics.setMin(new BigDecimal(1.00).setScale(2, BigDecimal.ROUND_HALF_UP));
        testStatistics.setCount(2);

        assertEquals(testStatistics, statisticsService.getStatistics());
    }

}
