package com.mskokava.currencyprediction.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CryptoCoin {

    BTC("bitcoin"), ETH("ethereum");

    private final String coinId;

    public static CryptoCoin getByCoinId(String coinId) {
        return Arrays.stream(values())
                .filter(coin -> coin.getCoinId().equals(coinId))
                .findFirst()
                .orElseThrow();
    }
}
