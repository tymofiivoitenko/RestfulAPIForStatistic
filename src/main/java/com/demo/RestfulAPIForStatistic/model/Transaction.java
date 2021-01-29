package com.demo.RestfulAPIForStatistic.model;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author tymofiivoitenko
 */
public class Transaction {

    private BigDecimal amount;
    private Instant timestamp;

    public Transaction() {
    }

    public Transaction(BigDecimal amount, Instant timestamp) {
        setAmount(amount);
        setTimestamp(timestamp);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.amount.setScale(2);
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
