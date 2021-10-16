package com.davidcastella.gnb_api.models;

public class TransactionResponseModel {
    private final String sku;
    private final Double amount;
    private final String currency;

    public TransactionResponseModel(String sku, Double amount, String currency) {
        this.sku = sku;
        this.amount = amount;
        this.currency = currency;
    }

    public String getSku() {
        return sku;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
