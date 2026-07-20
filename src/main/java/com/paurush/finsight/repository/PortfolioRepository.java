package com.paurush.finsight.repository;

import com.paurush.finsight.entity.Portfolio;
import com.paurush.finsight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    boolean existsByUserAndName(User user, String name);

    List<Portfolio> findByUser(User user);

    Optional<Portfolio> findByIdAndUser(Long id, User user);

}