package com.paurush.finsight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.paurush.finsight.entity.enums.AssetType;

@Getter
@AllArgsConstructor
public class AssetResponse {

    private Long id;

    private String symbol;

    private String name;

    private AssetType assetType;

    private BigDecimal quantity;

    private BigDecimal buyPrice;

    private BigDecimal currentPrice;

    private LocalDate purchaseDate;

    private LocalDateTime createdAt;
}