package com.medi.medicare;

public class rating_class {
    double rate;
    int totalReview;

    public rating_class() {
    }

    public rating_class(double rate, int totalReview) {
        this.rate = rate;
        this.totalReview = totalReview;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(int totalReview) {
        this.totalReview = totalReview;
    }
}
