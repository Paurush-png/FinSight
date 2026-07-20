package com.paurush.finsight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class DashboardResponse {

    private BigDecimal totalInvestment;

    private BigDecimal currentValue;

    private BigDecimal totalProfit;

    private BigDecimal overallReturn;

    private int portfolioCount;

    private int assetCount;
}