package com.mskokava.currencyprediction.service;

import com.mskokava.currencyprediction.service.enums.CryptoCoin;
import com.mskokava.currencyprediction.service.enums.TimePeriod;
import com.mskokava.currencyprediction.service.dto.PricesPrediction;

import javax.validation.constraints.NotNull;

public interface PredictionService {
    PricesPrediction receivePricesPrediction(@NotNull CryptoCoin coin,
                                             @NotNull TimePeriod analyzedPeriod,
                                             Integer daysForPrediction);
}
