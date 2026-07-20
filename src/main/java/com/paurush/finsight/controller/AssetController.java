package com.paurush.finsight.controller;

import com.paurush.finsight.dto.AssetRequest;
import com.paurush.finsight.dto.AssetResponse;
import com.paurush.finsight.service.interfaces.AssetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.paurush.finsight.dto.UpdateAssetRequest;

import java.util.List;

@RestController
@RequestMapping("/portfolios")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping("/{portfolioId}/assets")
    public ResponseEntity<AssetResponse> createAsset(
            @PathVariable Long portfolioId,
            @Valid @RequestBody AssetRequest request
    ) {

        AssetResponse response =
                assetService.createAsset(portfolioId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{portfolioId}/assets")
    public ResponseEntity<List<AssetResponse>> getAssets(
            @PathVariable Long portfolioId
    ) {

        List<AssetResponse> assets =
                assetService.getAssets(portfolioId);

        return ResponseEntity.ok(assets);
    }

    @PutMapping("/assets/{assetId}")
    public ResponseEntity<AssetResponse> updateAsset(
            @PathVariable Long assetId,
            @Valid @RequestBody UpdateAssetRequest request
    ) {

        AssetResponse response =
                assetService.updateAsset(assetId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/assets/{assetId}")
    public ResponseEntity<Void> deleteAsset(
            @PathVariable Long assetId
    ) {

        assetService.deleteAsset(assetId);

        return ResponseEntity.noContent().build();
    }
}