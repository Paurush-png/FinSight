package com.paurush.finsight.service.impl;

import com.paurush.finsight.dto.AssetRequest;
import com.paurush.finsight.dto.AssetResponse;
import com.paurush.finsight.dto.UpdateAssetRequest;
import com.paurush.finsight.entity.Asset;
import com.paurush.finsight.entity.Portfolio;
import com.paurush.finsight.entity.User;
import com.paurush.finsight.exception.AssetAlreadyExistsException;
import com.paurush.finsight.exception.AssetNotFoundException;
import com.paurush.finsight.exception.PortfolioNotFoundException;
import com.paurush.finsight.mapper.AssetMapper;
import com.paurush.finsight.repository.AssetRepository;
import com.paurush.finsight.repository.PortfolioRepository;
import com.paurush.finsight.service.interfaces.AssetService;
import com.paurush.finsight.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final PortfolioRepository portfolioRepository;
    private final SecurityUtils securityUtils;

    public AssetServiceImpl(
            AssetRepository assetRepository,
            PortfolioRepository portfolioRepository,
            SecurityUtils securityUtils
    ) {
        this.assetRepository = assetRepository;
        this.portfolioRepository = portfolioRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public AssetResponse createAsset(Long portfolioId, AssetRequest request) {

        Portfolio portfolio = getUserPortfolio(portfolioId);

        String symbol = request.getSymbol().trim().toUpperCase();

        if (assetRepository.existsByPortfolioAndSymbol(portfolio, symbol)) {
            throw new AssetAlreadyExistsException(
                    "Asset already exists in this portfolio"
            );
        }

        Asset asset = Asset.builder()
                .symbol(symbol)
                .name(request.getName().trim())
                .assetType(request.getAssetType())
                .quantity(request.getQuantity())
                .buyPrice(request.getBuyPrice())
                .currentPrice(request.getCurrentPrice())
                .purchaseDate(request.getPurchaseDate())
                .portfolio(portfolio)
                .build();

        return AssetMapper.toResponse(assetRepository.save(asset));
    }

    @Override
    public List<AssetResponse> getAssets(Long portfolioId) {

        Portfolio portfolio = getUserPortfolio(portfolioId);

        return assetRepository.findByPortfolio(portfolio)
                .stream()
                .map(AssetMapper::toResponse)
                .toList();
    }

    @Override
    public AssetResponse updateAsset(Long assetId, UpdateAssetRequest request) {

        Asset asset = getUserAsset(assetId);

        asset.setQuantity(request.getQuantity());
        asset.setBuyPrice(request.getBuyPrice());
        asset.setCurrentPrice(request.getCurrentPrice());
        asset.setPurchaseDate(request.getPurchaseDate());

        return AssetMapper.toResponse(assetRepository.save(asset));
    }

    @Override
    public void deleteAsset(Long assetId) {

        Asset asset = getUserAsset(assetId);

        assetRepository.delete(asset);
    }

    // ===================================================
    // Helper Methods
    // ===================================================

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

    private Asset getUserAsset(Long assetId) {

        User currentUser = securityUtils.getCurrentUser();

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() ->
                        new AssetNotFoundException("Asset not found"));

        if (!asset.getPortfolio().getUser().getId().equals(currentUser.getId())) {
            throw new AssetNotFoundException("Asset not found");
        }

        return asset;
    }
}