package com.davidcastella.gnb_api;

import com.davidcastella.gnb_api.service.GNBankService;
import retrofit2.Retrofit;

public class GNBankServiceAPI implements BankServiceAPI {
    @Override
    public Retrofit getAPI() {
        return GNBankService.create();
    }
}
