package org.example.auction.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.auction.Api.ApiResponse;
import org.example.auction.Model.*;
import org.example.auction.Repository.ProductRepository;
import org.example.auction.Repository.SellerRepository;
import org.example.auction.Service.AuctionService;
import org.example.auction.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionService auctionService;
    private final ProductService productService;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;

    Logger logger= LoggerFactory.getLogger(SellerController.class);


    @GetMapping("/get")
    public ResponseEntity getAllAuctions() {
        logger.info("inside getAllAuctions");

        return ResponseEntity.status(200).body(auctionService.getAllAuctions());
    }

    @PostMapping("/add-auction")
    public ResponseEntity addAuction(@RequestBody @Valid Auction auction) {
        auctionService.addAuction(auction);
        logger.info("inside addAuction");

        return ResponseEntity.status(200).body(new ApiResponse("added"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateAuction(@PathVariable Integer id, @RequestBody @Valid Auction auction) {
        auctionService.updateAuction(id, auction);
        logger.info("inside updateAuction");

        return ResponseEntity.status(200).body(new ApiResponse("updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAuction(@PathVariable Integer id) {
        auctionService.deleteAuction(id);
        logger.info("inside deleteAuction");

        return ResponseEntity.status(200).body(new ApiResponse("deleted"));
    }


    //Extra




    @PostMapping("/{auctionId}/bids")
    public ResponseEntity addBidsToAuction(@PathVariable Integer auctionId, @RequestBody BidRequest bidRequest) {
        List<User> users = bidRequest.getUsers();
        List<BigDecimal> prices = bidRequest.getPrices();
        logger.info("inside addBidsToAuction");


        if (users == null || prices == null || users.size() != prices.size()) {
            return ResponseEntity.badRequest().body("Invalid request");
        }

        auctionService.addBidsToAuction(auctionId, users, prices);
        return ResponseEntity.ok("Bids added successfully");
    }



    @GetMapping("/auction/{auctionId}/winning-bid")
    public ResponseEntity getWinningBid(@PathVariable Integer auctionId) {
        Optional<Bid> winningBidOptional = auctionService.getWinningBid(auctionId);
        logger.info("inside getWinningBid");

        if (winningBidOptional.isPresent()) {
            Bid winningBid = winningBidOptional.get();
            return ResponseEntity.ok(winningBid);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

        @PostMapping("/complete/{auctionId}")
        public ResponseEntity completeAuction(@PathVariable Integer auctionId) {
            auctionService.completeAuction(auctionId);
            logger.info("inside completeAuction");

            return ResponseEntity.ok("Auction completed successfully");
        }

    @GetMapping("/ended/count")
    public int countEndedAuctions() {
        logger.info("inside countEndedAuctions");

        return auctionService.countEndedAuctions();
    }



}
