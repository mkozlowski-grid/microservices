package com.example.productservice.client;

import com.example.productservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "CATALOG-SERVICE", url = "http://localhost:8083/catalog-service", fallbackFactory = CatalogClientFallbackFactory.class)
public interface CatalogClient {
    @GetMapping(value = "/id/{uniq_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Product findByUniqId(@PathVariable("uniq_id") String uniqId);

    @GetMapping("/sku/{sku}")
    List<Product> findBySku(@PathVariable("sku") String sku);
}
