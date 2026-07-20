package com.paurush.finsight.service.impl;

import com.paurush.finsight.dto.DashboardResponse;
import com.paurush.finsight.repository.PortfolioRepository;
import com.paurush.finsight.repository.UserRepository;
import com.paurush.finsight.service.interfaces.DashboardService;
import com.paurush.finsight.util.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final SecurityUtils securityUtils;

    public DashboardServiceImpl(UserRepository userRepository, PortfolioRepository portfolioRepository, SecurityUtils securityUtils) {
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public DashboardResponse getDashboard() {
        return null;
    }
}
