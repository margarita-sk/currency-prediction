package com.mskokava.currencyprediction.client;

import com.mskokava.currencyprediction.service.enums.CryptoCoin;
import com.mskokava.currencyprediction.service.enums.Currency;
import com.mskokava.currencyprediction.service.dto.PricesByCurrencyDto;
import com.mskokava.currencyprediction.service.enums.TimePeriod;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface CurrencyClient {

    PricesByCurrencyDto receiveHistoryCoinPrices(@NotNull TimePeriod period, @NotNull CryptoCoin coin);

    BigDecimal receiveActualCoinPrice(@NotNull CryptoCoin coin, @NotNull Currency currency);
}
