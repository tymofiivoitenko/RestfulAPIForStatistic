package com.demo.RestfulAPIForStatistic.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author tymofiivoitenko
 */
public class Statistics {
    private BigDecimal sum;
    private BigDecimal average;
    private BigDecimal max;
    private BigDecimal min;
    private long count;

    public Statistics() {
    }

    public Statistics(BigDecimal sum, BigDecimal average, BigDecimal max, BigDecimal min, long count) {
        this.sum = sum;
        this.average = average;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return count == that.count &&
                Objects.equals(sum, that.sum) &&
                Objects.equals(average, that.average) &&
                Objects.equals(max, that.max) &&
                Objects.equals(min, that.min);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, average, max, min, count);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "sum=" + sum +
                ", average=" + average +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }
}
