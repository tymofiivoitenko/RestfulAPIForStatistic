package com.demo.RestfulAPIForStatistic;

import com.demo.RestfulAPIForStatistic.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author tymofiivoitenko
 */
@SpringBootApplication
@EnableScheduling
public class RestfulApiForStatisticApplication {

    @Autowired
    private StatisticsService statisticsService;

    public static void main(String[] args) {
        SpringApplication.run(RestfulApiForStatisticApplication.class, args);
    }

    // Run Statistics Updater
    @Bean
    public ApplicationRunner startUpdatingStatistics() {
        return args -> statisticsService.updateStatistics();
    }

}
