package org.example.auction.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.auction.Api.ApiResponse;

import org.example.auction.Model.Product;
import org.example.auction.Service.ProductService;
import org.example.auction.Service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final SellerService sellerService;

    Logger logger= LoggerFactory.getLogger(SellerController.class);

    @GetMapping("/get")
    public ResponseEntity getAllProducts() {
        logger.info("inside getAllProducts");

        return ResponseEntity.status(200).body(productService.getAllProducts());
    }


    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product) {
        productService.addProduct(product);
        logger.info("inside addProduct");

        return ResponseEntity.status(200).body(new ApiResponse("Product added"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody @Valid Product product) {
        productService.updateProduct(id, product);
        logger.info("inside updateProduct");

        return ResponseEntity.status(200).body(new ApiResponse("Product updated"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        logger.info("inside deleteProduct");
        return ResponseEntity.status(200).body(new ApiResponse("Product deleted"));
    }


    //Etxra


    @GetMapping("/products/minimum-price")
    public ResponseEntity<List<Product>> findByMinimumPrice(@RequestParam("minPrice") Integer minPrice) {
        List<Product> products = productService.findByMinimumPrice(productService.getAllProducts(), minPrice);
        logger.info("inside findByMinimumPrice");

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }



    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Product>> getProductsBySellerId(@PathVariable Integer sellerId) {
        List<Product> products = productService.findProductsBySellerId(sellerId);
        logger.info("inside getProductsBySellerId");

        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{productId}/auctions/count")
    public int countProductsInAuction(@PathVariable Integer productId) {

        logger.info("inside countProductsInAuction");

        return productService.countProductsInAuction(productId);
    }

}

