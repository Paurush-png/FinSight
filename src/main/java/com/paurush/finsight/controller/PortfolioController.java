package com.paurush.finsight.controller;

import com.paurush.finsight.dto.PortfolioAnalyticsResponse;
import com.paurush.finsight.dto.PortfolioRequest;
import com.paurush.finsight.dto.PortfolioResponse;
import com.paurush.finsight.dto.PortfolioSummaryResponse;
import com.paurush.finsight.entity.Portfolio;
import com.paurush.finsight.service.interfaces.PortfolioService;
import com.paurush.finsight.dto.UpdatePortfolioRequest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // ==========================
    // Create Portfolio
    // ==========================

    @PostMapping
    public ResponseEntity<PortfolioResponse> createPortfolio(
            @Valid @RequestBody PortfolioRequest request
    ) {

        Portfolio portfolio = portfolioService.createPortfolio(request);

        PortfolioResponse response = new PortfolioResponse(
                portfolio.getId(),
                portfolio.getName(),
                portfolio.getDescription(),
                portfolio.getCreatedAt()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ==========================
    // Get All Portfolios
    // ==========================

    @GetMapping
    public ResponseEntity<List<PortfolioSummaryResponse>> getAllPortfolios() {

        List<Portfolio> portfolios = portfolioService.getAllPortfolios();

        List<PortfolioSummaryResponse> response = portfolios.stream()
                .map(portfolio -> new PortfolioSummaryResponse(
                        portfolio.getId(),
                        portfolio.getName(),
                        portfolio.getDescription(),
                        portfolio.getCreatedAt()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioResponse> getPortfolioById(
            @PathVariable Long id
    ) {

        Portfolio portfolio = portfolioService.getPortfolioById(id);

        PortfolioResponse response = new PortfolioResponse(
                portfolio.getId(),
                portfolio.getName(),
                portfolio.getDescription(),
                portfolio.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortfolioResponse> updatePortfolio(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePortfolioRequest request
    ) {

        PortfolioResponse response =
                portfolioService.updatePortfolio(id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<PortfolioAnalyticsResponse> getPortfolioSummary(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                portfolioService.getPortfolioSummary(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(
            @PathVariable Long id
    ) {

        portfolioService.deletePortfolio(id);

        return ResponseEntity.noContent().build();
    }
}