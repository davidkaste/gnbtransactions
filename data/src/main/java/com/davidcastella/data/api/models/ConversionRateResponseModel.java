package com.davidcastella.data.api.models;

public class ConversionRateResponseModel {
    private final String from;
    private final String to;
    private final Double rate;

    public ConversionRateResponseModel(String from, String to, Double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Double getRate() {
        return rate;
    }
}
