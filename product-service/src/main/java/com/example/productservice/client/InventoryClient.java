package com.example.productservice.client;

import com.example.productservice.model.ProductStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "inventory-service", url = "http://inventory-service:8085/inventory-service", fallbackFactory = InventoryClientFallbackFactory.class)
public interface InventoryClient {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    List<ProductStatus> findByUniqIds(@RequestBody List<String> uniqIds);
}
