package com.paurush.finsight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PortfolioAnalyticsResponse {

    private Long portfolioId;

    private String portfolioName;

    private BigDecimal totalInvestment;

    private BigDecimal currentValue;

    private BigDecimal profitLoss;

    private BigDecimal profitLossPercentage;

    private int assetCount;
}