package com.demo.RestfulAPIForStatistic;

import com.demo.RestfulAPIForStatistic.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void isCorrectTransactionCreated() throws Exception {
        Transaction transaction = new Transaction(new BigDecimal(12.3343), Instant.now());

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isCreated());
    }

    @Test
    public void isTransactionWithOldDateFailedCreation() throws Exception {
        Transaction transaction = new Transaction(new BigDecimal(12.3343), Instant.parse("2018-07-17T09:59:51.312Z"));

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void isTransactionWithDateInFutureFailedCreation() throws Exception {
        Transaction transaction = new Transaction(new BigDecimal(12.3343), Instant.now());

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isCreated());
    }

    @Test
    public void areTransactionWithNullArgumentsFailedCreation() throws Exception {

        // Check for amount null
        String postRequest = "{\"amount\":\"\",\"timestamp\":\"2018-07-17T09:59:51.312Z\"}";

        mockMvc.perform(post("/transactions")
                .content(postRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        // Check for timestamp null
        postRequest = "{\"amount\":\"3\",\"timestamp\":\"\"}";

        mockMvc.perform(post("/transactions")
                .content(postRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void areTransactionWithInvalidArgumentsFailedCreation() throws Exception {

        // Check for invalid amount
        String postRequest = "{\"amount\": \"#\",\"timestamp\":\"2021-01-29T20:10:51.312Z\"}";

        mockMvc.perform(post("/transactions")
                .content(postRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        // Check for invalid timestamp
        postRequest = "{\"amount\":\"3\",\"timestamp\":\"#\"" + "}";

        mockMvc.perform(post("/transactions")
                .content(postRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void isStatisticsEmptyForNoCreatedTransactions() throws Exception {

        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"sum\":0,\"average\":0,\"max\":0,\"min\":0,\"count\":0}"));
    }

    @Test
    public void isStatisticsCorrectWithSeveralTransactions() throws Exception {
        Transaction transaction = new Transaction(new BigDecimal(40.4735892749287), Instant.now());
        Transaction transaction1 = new Transaction(new BigDecimal(1000), Instant.now());
        Transaction transaction2 = new Transaction(new BigDecimal(0), Instant.now());

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)));

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction1)));

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction2)));

        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"sum\":1040.47,\"average\":346.82,\"max\":1000,\"min\":0,\"count\":3}"));
    }

    @Test
    public void areTransactionSuccessfullyDeleted() throws Exception {
        // Add transactions
        Transaction transaction = new Transaction(new BigDecimal(1), Instant.now());
        Transaction transaction1 = new Transaction(new BigDecimal(2), Instant.now());

        // Send post requests
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction1)))
                .andExpect(status().isCreated());

        // Check if transactions are saved
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"sum\":3,\"average\":1.50,\"max\":2,\"min\":1,\"count\":2}"));

        // Delete transactions
        mockMvc.perform(delete("/transactions"))
                .andExpect(status().isNoContent());

        // Delete if no transactions are successfully deleted
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"sum\":0,\"average\":0,\"max\":0,\"min\":0,\"count\":0}"));
    }
}
