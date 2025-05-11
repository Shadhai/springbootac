package com.project.service;
import com.project.entity.AuctionStatus;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Auction;
import com.project.repository.AuctionRepository;
import com.project.entity.AuctionStatus;
@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    public Auction createAuction(Auction auction) {
        auction.setStatus(AuctionStatus.UPCOMING);
        return auctionRepository.save(auction);
    }

    public Optional<Auction> getAuctionById(Long id) {
        return auctionRepository.findById(id);
    }

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    public Auction updateAuction(Long id, Auction updatedAuction) {
        return auctionRepository.findById(id)
                .map(auction -> {
                    auction.setItemName(updatedAuction.getItemName());
                    auction.setDescription(updatedAuction.getDescription());
                    auction.setStartingPrice(updatedAuction.getStartingPrice());
                    auction.setReservePrice(updatedAuction.getReservePrice());
                    auction.setStartTime(updatedAuction.getStartTime());
                    auction.setEndTime(updatedAuction.getEndTime());
                    auction.setImageUrl(updatedAuction.getImageUrl());
                    auction.setStatus(updatedAuction.getStatus());
                    auction.setStartingBid(updatedAuction.getStartingBid());
                    auction.setCategory(updatedAuction.getCategory());
                    return auctionRepository.save(auction);
                })
                .orElse(null);
    }
    public long countAuctionsByStatus(String status) {
        try {
            AuctionStatus auctionStatus = AuctionStatus.valueOf(status.toUpperCase()); // Convert to enum, handle potential errors
            return auctionRepository.countByStatus(auctionStatus); // Assuming you'll add this method to your repository
        } catch (IllegalArgumentException e) {
            // Handle invalid status string
            throw new IllegalArgumentException("Invalid auction status: " + status);
        }
    }

    public void deleteAuction(Long id) {
        auctionRepository.deleteById(id);
    }

    public Auction approveAuction(Long id) {
        return auctionRepository.findById(id)
                .map(auction -> {
                    auction.setStatus(AuctionStatus.ACTIVE);
                    return auctionRepository.save(auction);
                })
                .orElse(null);
    }

    public Auction endAuction(Long id) {
        return auctionRepository.findById(id)
                .map(auction -> {
                    auction.setStatus(AuctionStatus.ENDED);
                    return auctionRepository.save(auction);
                })
                .orElse(null);
    }
 // Add this method to your AuctionService class

    public Auction updateAuctionWithImage(Long id, MultipartFile imageFile, String itemName, String description, 
                                         Double startingPrice, Double startingBid, String endTime, String startTime,
                                         String category, String status, Double reservePrice) {
        try {
            // Get the existing auction
            Optional<Auction> existingAuction = auctionRepository.findById(id);
            if (!existingAuction.isPresent()) {
                return null;
            }
            
            Auction auction = existingAuction.get();
            
            // Process and save the image
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            String uploadDir = "uploads/images/";
            
            // Create directory if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Save the file
            String filePath = uploadDir + fileName;
            File dest = new File(filePath);
            imageFile.transferTo(dest);
            
            // Update the auction properties
            auction.setItemName(itemName);
            auction.setDescription(description);
            auction.setStartingPrice(startingPrice);
            auction.setStartingBid(startingBid);
            
            // Parse the datetime strings
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            auction.setEndTime(LocalDateTime.parse(endTime, formatter));
            auction.setStartTime(LocalDateTime.parse(startTime, formatter));
            
            auction.setCategory(category);
            auction.setStatus(AuctionStatus.valueOf(status));
            auction.setReservePrice(reservePrice);
            auction.setImageUrl("/uploads/images/" + fileName);
            
            return auctionRepository.save(auction);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update auction with image: " + e.getMessage());
        }
    }
}
