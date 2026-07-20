package com.paurush.finsight.service.interfaces;

import com.paurush.finsight.dto.PortfolioAnalyticsResponse;
import com.paurush.finsight.dto.PortfolioRequest;
import com.paurush.finsight.dto.PortfolioResponse;
import com.paurush.finsight.dto.UpdatePortfolioRequest;
import com.paurush.finsight.entity.Portfolio;

import java.util.List;

public interface PortfolioService {

    Portfolio createPortfolio(PortfolioRequest request);

    List<Portfolio> getAllPortfolios();

    Portfolio getPortfolioById(Long id);

    PortfolioResponse updatePortfolio(
            Long portfolioId,
            UpdatePortfolioRequest request
    );

    void deletePortfolio(Long portfolioId);

    PortfolioAnalyticsResponse getPortfolioSummary(Long portfolioId);
}
