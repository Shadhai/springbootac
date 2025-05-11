package com.project.repository;

import com.project.entity.Auction;
import com.project.entity.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    long countByStatus(AuctionStatus status);
}