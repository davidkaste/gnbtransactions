package com.davidcastella.gnb_api;

import com.davidcastella.gnb_api.service.GNBankService;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class GNBankServiceAPI implements BankServiceAPI {

    @Inject
    public GNBankServiceAPI() {
    }

    @Override
    public Retrofit getAPI() {
        return GNBankService.create();
    }
}
