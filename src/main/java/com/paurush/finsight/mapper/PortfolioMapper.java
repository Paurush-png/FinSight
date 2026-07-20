package com.paurush.finsight.mapper;

import com.paurush.finsight.dto.PortfolioResponse;
import com.paurush.finsight.entity.Portfolio;

public class PortfolioMapper {

    private PortfolioMapper() {
    }

    public static PortfolioResponse toResponse(Portfolio portfolio) {

        return new PortfolioResponse(
                portfolio.getId(),
                portfolio.getName(),
                portfolio.getDescription(),
                portfolio.getCreatedAt()
        );
    }
}