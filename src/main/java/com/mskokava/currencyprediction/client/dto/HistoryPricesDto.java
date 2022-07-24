package com.mskokava.currencyprediction.client.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class HistoryPricesDto {
    private List<List<BigDecimal>> chart;
}
