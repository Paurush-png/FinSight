package com.paurush.finsight.service.impl;

import com.paurush.finsight.dto.*;
import com.paurush.finsight.entity.Asset;
import com.paurush.finsight.entity.Portfolio;
import com.paurush.finsight.entity.User;
import com.paurush.finsight.exception.InvalidCredentialsException;
import com.paurush.finsight.exception.PortfolioAlreadyExistsException;
import com.paurush.finsight.exception.PortfolioNotFoundException;
import com.paurush.finsight.repository.PortfolioRepository;
import com.paurush.finsight.repository.UserRepository;
import com.paurush.finsight.service.interfaces.PortfolioService;
import com.paurush.finsight.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    public PortfolioServiceImpl(
            PortfolioRepository portfolioRepository,
            UserRepository userRepository,
            SecurityUtils securityUtils
    ) {
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public Portfolio createPortfolio(PortfolioRequest request) {

        String email = securityUtils.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException("User not found"));

        String portfolioName = request.getName().trim();

        if (portfolioRepository.existsByUserAndName(user, portfolioName)) {
            throw new PortfolioAlreadyExistsException(
                    "Portfolio with this name already exists");
        }

        Portfolio portfolio = Portfolio.builder()
                .name(portfolioName)
                .description(request.getDescription())
                .user(user)
                .build();

        return portfolioRepository.save(portfolio);
    }

    @Override
    public List<Portfolio> getAllPortfolios() {

        String email = securityUtils.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException("User not found"));

        return portfolioRepository.findByUser(user);
    }

    @Override
    public Portfolio getPortfolioById(Long id) {
        return getUserPortfolio(id);
    }

    @Override
    public PortfolioResponse updatePortfolio(
            Long portfolioId,
            UpdatePortfolioRequest request
    ) {

        Portfolio portfolio = getUserPortfolio(portfolioId);

        String newName = request.getName().trim();

        if (!portfolio.getName().equalsIgnoreCase(newName)
                && portfolioRepository.existsByUserAndName(
                portfolio.getUser(),
                newName
        )) {

            throw new PortfolioAlreadyExistsException(
                    "Portfolio with this name already exists"
            );
        }

        portfolio.setName(newName);
        portfolio.setDescription(request.getDescription());

        Portfolio updatedPortfolio = portfolioRepository.save(portfolio);

        return new PortfolioResponse(
                updatedPortfolio.getId(),
                updatedPortfolio.getName(),
                updatedPortfolio.getDescription(),
                updatedPortfolio.getCreatedAt()
        );
    }

    @Override
    public void deletePortfolio(Long portfolioId) {

        Portfolio portfolio = getUserPortfolio(portfolioId);

        portfolioRepository.delete(portfolio);
    }

    @Override
    public PortfolioAnalyticsResponse getPortfolioSummary(Long portfolioId) {

        Portfolio portfolio = getUserPortfolio(portfolioId);

        List<Asset> assets = portfolio.getAssets();

        BigDecimal totalInvestment = calculateTotalInvestment(assets);

        BigDecimal currentValue = calculateCurrentValue(assets);

        BigDecimal profitLoss = currentValue.subtract(totalInvestment);

        BigDecimal profitPercentage = calculateProfitPercentage(
                totalInvestment,
                profitLoss
        );

        return new PortfolioAnalyticsResponse(
                portfolio.getId(),
                portfolio.getName(),
                totalInvestment,
                currentValue,
                profitLoss,
                profitPercentage,
                assets.size()
        );
    }

    // ==========================
    // Helper Methods
    // ==========================

    private Portfolio getUserPortfolio(Long portfolioId) {

        User currentUser = securityUtils.getCurrentUser();

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() ->
                        new PortfolioNotFoundException("Portfolio not found"));

        if (!portfolio.getUser().getId().equals(currentUser.getId())) {
            throw new PortfolioNotFoundException("Portfolio not found");
        }

        return portfolio;
    }

    private BigDecimal calculateTotalInvestment(List<Asset> assets) {

        return assets.stream()
                .map(asset ->
                        asset.getQuantity()
                                .multiply(asset.getBuyPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateCurrentValue(List<Asset> assets) {

        return assets.stream()
                .map(asset ->
                        asset.getQuantity()
                                .multiply(asset.getCurrentPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateProfitPercentage(
            BigDecimal investment,
            BigDecimal profit
    ) {

        if (investment.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return profit.multiply(BigDecimal.valueOf(100))
                .divide(investment, 2, RoundingMode.HALF_UP);
    }
}