package com.example.productservice.controller;

import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products-service")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping("/id/{uniqId}")
    public ResponseEntity<Product> findAvailableProductById(@PathVariable("uniqId") String uniqId){
        log.info("findAvailableProductById for " + uniqId);
        return ResponseEntity.ok(productService.findAvailableProductByUniqId(uniqId));
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<List<Product>> findAvailableProductsBySku(@PathVariable("sku") String sku){
        log.info("findAvailableProductsBySku for " + sku);
        return ResponseEntity.ok(productService.findAvailableProductsBySkus(sku));
    }
}
