package com.davidcastella.gnb_api;

import com.davidcastella.gnb_api.service.GNBTransactionService;
import retrofit2.Retrofit;

public class GNBTransactionServiceAPI implements TransactionServiceAPI {
    @Override
    public Retrofit getAPI() {
        return GNBTransactionService.create();
    }
}
