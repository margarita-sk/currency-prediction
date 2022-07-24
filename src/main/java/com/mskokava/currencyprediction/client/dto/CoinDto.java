package com.mskokava.currencyprediction.client.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinDto {

    private CoinPrice coin;

    @Data
    public static class CoinPrice {
        private BigDecimal price;
    }
}
