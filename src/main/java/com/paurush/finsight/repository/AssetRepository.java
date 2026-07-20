package com.paurush.finsight.repository;

import com.paurush.finsight.entity.Asset;
import com.paurush.finsight.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findByPortfolio(Portfolio portfolio);

    boolean existsByPortfolioAndSymbol(
            Portfolio portfolio,
            String symbol
    );
}