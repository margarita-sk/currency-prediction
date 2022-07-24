package com.mskokava.currencyprediction.service.dto;

import com.mskokava.currencyprediction.service.enums.CryptoCoin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PricesPrediction {

    private CryptoCoin coin;

    private Map<LocalDate, BigDecimal> timePrices;

}
