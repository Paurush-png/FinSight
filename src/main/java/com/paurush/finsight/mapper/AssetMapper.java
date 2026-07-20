package com.paurush.finsight.mapper;

import com.paurush.finsight.dto.AssetResponse;
import com.paurush.finsight.entity.Asset;

public class AssetMapper {

    private AssetMapper() {
    }

    public static AssetResponse toResponse(Asset asset) {

        return new AssetResponse(
                asset.getId(),
                asset.getSymbol(),
                asset.getName(),
                asset.getAssetType(),
                asset.getQuantity(),
                asset.getBuyPrice(),
                asset.getCurrentPrice(),
                asset.getPurchaseDate(),
                asset.getCreatedAt()
        );
    }
}