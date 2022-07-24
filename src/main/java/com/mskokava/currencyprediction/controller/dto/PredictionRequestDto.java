package com.mskokava.currencyprediction.controller.dto;

import com.mskokava.currencyprediction.service.enums.CryptoCoin;
import com.mskokava.currencyprediction.service.enums.TimePeriod;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
public class PredictionRequestDto {
    private CryptoCoin coin;
    private TimePeriod analyzedPeriod;

    @PositiveOrZero
    private int predictedPeriod;

    public void setCoin(String coin) {
        this.coin = CryptoCoin.getByCoinId(coin);
    }

    public void setAnalyzedPeriod(String analyzedPeriod) {
        this.analyzedPeriod = TimePeriod.getByPeriodId(analyzedPeriod);
    }
}
