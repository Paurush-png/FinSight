package com.paurush.finsight.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateAssetRequest {

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.000001", message = "Quantity must be greater than 0")
    private BigDecimal quantity;

    @NotNull(message = "Buy price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal buyPrice;

    @NotNull(message = "Current price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal currentPrice;

    @NotNull(message = "Purchase date is required")
    private LocalDate purchaseDate;

}