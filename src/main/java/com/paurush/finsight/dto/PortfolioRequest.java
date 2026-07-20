package com.paurush.finsight.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioRequest {

    @NotBlank(message = "Portfolio name is required")
    private String name;

    private String description;

}