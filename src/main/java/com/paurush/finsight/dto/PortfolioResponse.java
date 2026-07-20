package com.paurush.finsight.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PortfolioResponse {

    private Long id;

    private String name;

    private String description;

    private LocalDateTime createdAt;

}