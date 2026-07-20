package com.paurush.finsight.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.paurush.finsight.entity.enums.AssetType;

@Getter
@Setter
public class AssetRequest {

    @NotBlank(message = "Symbol is required")
    private String symbol;

    @NotBlank(message = "Asset name is required")
    private String name;

    @NotNull(message = "Asset type is required")
    private AssetType assetType;
    
    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0001", message = "Quantity must be greater than 0")
    private BigDecimal quantity;

    @NotNull(message = "Buy price is required")
    @DecimalMin(value = "0.01", message = "Buy price must be greater than 0")
    private BigDecimal buyPrice;

    @NotNull(message = "Current price is required")
    @DecimalMin(value = "0.01", message = "Current price must be greater than 0")
    private BigDecimal currentPrice;

    @NotNull(message = "Purchase date is required")
    private LocalDate purchaseDate;
}