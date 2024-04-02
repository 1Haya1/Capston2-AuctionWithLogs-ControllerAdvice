package org.example.auction.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.auction.Api.ApiResponse;
import org.example.auction.Model.Product;
import org.example.auction.Model.Seller;
import org.example.auction.Service.SellerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    //sl4j عشان اعرف من البدايه للنهايه شسويت في البوست مان
    Logger logger= LoggerFactory.getLogger(SellerController.class);



    @GetMapping("/get")
    public ResponseEntity getAllSellers() {
        logger.info("inside get all seller");

        return ResponseEntity.status(200).body(sellerService.getAllSellers());
    }


    @PostMapping("/add")
    public ResponseEntity addSeller(@RequestBody @Valid Seller seller) {
        sellerService.addSeller(seller);

        logger.info("inside add seller");

        return ResponseEntity.status(200).body(new ApiResponse("Seller added"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateSeller(@PathVariable Integer id, @RequestBody @Valid Seller seller) {
        sellerService.updateSeller(id, seller);

        logger.info("update seller");

        return ResponseEntity.ok(new ApiResponse("Seller updated successfully"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteSeller(@PathVariable Integer id) {
        sellerService.deleteSeller(id);
        logger.info("delete seller");
        return ResponseEntity.status(200).body(new ApiResponse("Seller deleted"));
    }


    @GetMapping("/get-seller/{id}")
    public ResponseEntity getSellerById(@PathVariable Integer id) {
        Seller seller = sellerService.getSellerById(id);
        logger.info("get seller by id");
        if (seller == null) {
            return ResponseEntity.status(400).body(new ApiResponse("id not found"));
        }
        return ResponseEntity.ok(seller);
    }






    //Extra


    @GetMapping("/sellers/active-products")
    public Map<Seller, List<Product>> findAllSellersWithActiveProducts() {
        logger.info("inside findAllSellersWithActiveProducts");
        return sellerService.findAllSellersWithActiveProducts();
    }

    @GetMapping("/get/{sellerId}/products-active")
    public ResponseEntity<List<Product>> getSellersWithActiveProducts(@PathVariable Integer sellerId) {
        List<Product> activeProducts = sellerService.getSellersWithActiveProducts(sellerId);
        logger.info("inside getSellersWithActiveProducts");
        return ResponseEntity.ok().body(activeProducts);
    }

    @GetMapping("/top-sellers")
    public List<Seller> getTopSellers(@RequestParam(name = "limit", defaultValue = "10") int limit) {
        logger.info("inside getTopSellers");
        return sellerService.findTopSellingSellers(limit);
    }









}
