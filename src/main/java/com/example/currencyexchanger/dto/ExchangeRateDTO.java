package com.example.currencyexchanger.dto;

public class ExchangeRateDTO {
    private int id;
    private CurrencyDTO BaseCurrencyId;
    private CurrencyDTO TargetCurrencyId;
    private double rate;

    public ExchangeRateDTO(int id, CurrencyDTO baseCurrencyId, CurrencyDTO targetCurrencyId, double rate) {
        this.id = id;
        BaseCurrencyId = baseCurrencyId;
        TargetCurrencyId = targetCurrencyId;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CurrencyDTO getBaseCurrencyId() {
        return BaseCurrencyId;
    }

    public void setBaseCurrencyId(CurrencyDTO baseCurrencyId) {
        BaseCurrencyId = baseCurrencyId;
    }

    public CurrencyDTO getTargetCurrencyId() {
        return TargetCurrencyId;
    }

    public void setTargetCurrencyId(CurrencyDTO targetCurrencyId) {
        TargetCurrencyId = targetCurrencyId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


}
