package com.example.productservice.client;


import com.example.productservice.model.Product;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Slf4j
@RequiredArgsConstructor
class CatalogClientFallbackWithFactory implements CatalogClient{
    private final Throwable cause;
    @Override
    public Product findByUniqId(String uniqId) {
        log.error("Fallback for findByUniqId triggered for uniqId: {}", uniqId, cause);
        if (cause instanceof FeignException.NotFound) {
            throw new ResponseStatusException(NOT_FOUND, "Product not found: " + uniqId, cause);
        }
        else if (cause instanceof CallNotPermittedException) {
            log.info("Circuit breaker pattern stopped from calling remote catalog-service: " + cause.getMessage());
            throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Circuit breaker pattern stopped from calling remote catalog-service", cause);
        }
        throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Service is unavailable, cannot retrieve product with uniqId: " + uniqId, cause);
    }

    @Override
    public List<Product> findBySku(String sku) {
        log.error("Fallback for findBySku triggered for sku: {}", sku, cause);
        if (cause instanceof FeignException.NotFound) {
            throw new ResponseStatusException(NOT_FOUND, "Products not found for sku: " + sku, cause);
        }
        else if (cause instanceof CallNotPermittedException) {
            log.info("Circuit breaker pattern stopped from calling remote catalog-service: " + cause.getMessage());
            throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Circuit breaker pattern stopped from calling remote catalog-service", cause);        }
        throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Service is unavailable, cannot retrieve products for sku: " + sku, cause);
    }
}
