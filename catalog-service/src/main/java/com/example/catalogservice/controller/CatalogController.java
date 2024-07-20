package com.example.catalogservice.controller;

import com.example.catalogservice.model.Product;
import com.example.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalog-service")
public class CatalogController {
    private final CatalogService catalogService;

    @GetMapping("/id/{uniq_id}")
    public ResponseEntity<Mono<Product>> findByUniqId(@PathVariable String uniq_id){
        return ResponseEntity.ok(catalogService.findProductByUniqId(uniq_id));
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<Flux<Product>> findBySku(@PathVariable String sku){
        return ResponseEntity.ok(catalogService.findProductBySku(sku));
    }
}
