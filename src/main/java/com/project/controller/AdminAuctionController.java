package com.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.entity.Auction;
import com.project.service.AuctionService;

@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/admin/auctions")
public class AdminAuctionController {

    @Autowired
    private AuctionService auctionService;

    @PostMapping
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        Auction createdAuction = auctionService.createAuction(auction);
        return new ResponseEntity<>(createdAuction, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        List<Auction> auctions = auctionService.getAllAuctions();
        return new ResponseEntity<>(auctions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Auction>> getAuctionById(@PathVariable Long id) {
        Optional<Auction> auction = auctionService.getAuctionById(id);
        if (auction.isPresent()) {
            return new ResponseEntity<>(auction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auction> updateAuction(@PathVariable Long id, @RequestBody Auction updatedAuction) {
        Auction savedAuction = auctionService.updateAuction(id, updatedAuction);
        if (savedAuction != null) {
            return new ResponseEntity<>(savedAuction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        auctionService.deleteAuction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Auction> approveAuction(@PathVariable Long id) {
        Auction approvedAuction = auctionService.approveAuction(id);
        if (approvedAuction != null) {
            return new ResponseEntity<>(approvedAuction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getAuctionCountByStatus(@RequestParam("status") String status) {
        try {
            long count = auctionService.countAuctionsByStatus(status); // Assuming you'll add this method to your service
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Handle invalid status values
        }
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<Auction> endAuction(@PathVariable Long id) {
        Auction endedAuction = auctionService.endAuction(id);
        if (endedAuction != null) {
            return new ResponseEntity<>(endedAuction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
 // Add this method to your AdminAuctionController class

    @PutMapping("/upload/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<Auction> updateAuctionWithImage(
            @PathVariable Long id,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("itemName") String itemName,
            @RequestParam("description") String description,
            @RequestParam("startingPrice") Double startingPrice,
            @RequestParam("startingBid") Double startingBid,
            @RequestParam("endTime") String endTime,
            @RequestParam("startTime") String startTime,
            @RequestParam("category") String category,
            @RequestParam("status") String status,
            @RequestParam(value = "reservePrice", required = false) Double reservePrice) {
        
        try {
            Auction updatedAuction = auctionService.updateAuctionWithImage(id, imageFile, itemName, description, 
                startingPrice, startingBid, endTime, startTime, category, status, reservePrice);
            
            if (updatedAuction != null) {
                return new ResponseEntity<>(updatedAuction, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}