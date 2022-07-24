package com.mskokava.currencyprediction.service.dto;

import com.mskokava.currencyprediction.service.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PricesByCurrencyDto {
    private Currency currency;
    private List<BigDecimal> prices;
}
