package com.paurush.finsight.service.interfaces;

import com.paurush.finsight.dto.AssetRequest;
import com.paurush.finsight.dto.AssetResponse;
import com.paurush.finsight.dto.UpdateAssetRequest;

import java.util.List;

public interface AssetService {

    AssetResponse createAsset(Long portfolioId, AssetRequest request);

    List<AssetResponse> getAssets(Long portfolioId);

    AssetResponse updateAsset(Long assetId, UpdateAssetRequest request);

    void deleteAsset(Long assetId);
}