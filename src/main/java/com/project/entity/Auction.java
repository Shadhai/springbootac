package com.project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double startingPrice;

    private Double reservePrice;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private AuctionStatus status;

    private Double startingBid;
    private String category;

    // Constructors, Getters, and Setters (as provided in the previous full code)
  // Add this field

    // Constructors (update these to include the new fields)
    public Auction() {
    }

    public Auction(String itemName, String description, Double startingPrice, Double reservePrice, LocalDateTime startTime, LocalDateTime endTime, String imageUrl, AuctionStatus status, Double startingBid, String category) {
        this.itemName = itemName;
        this.description = description;
        this.startingPrice = startingPrice;
        this.reservePrice = reservePrice;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imageUrl = imageUrl;
        this.status = status;
        this.startingBid = startingBid;
        this.category = category;
    }

    // Getters (add getters for the new fields)
    public Double getStartingBid() {
        return startingBid;
    }

    public String getCategory() {
        return category;
    }

    // Setters (add setters for the new fields)
    public void setStartingBid(Double startingBid) {
        this.startingBid = startingBid;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Existing getters and setters...
    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public Double getStartingPrice() {
        return startingPrice;
    }

    public Double getReservePrice() {
        return reservePrice;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartingPrice(Double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public void setReservePrice(Double reservePrice) {
        this.reservePrice = reservePrice;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }
}