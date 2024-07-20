package com.example.productservice.client;

import com.example.productservice.model.Product;
import com.example.productservice.model.ProductStatus;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
@Slf4j
@RequiredArgsConstructor
public class InventoryClientFallbackWithFactory implements InventoryClient{
    private final Throwable cause;
    @Override
    public List<ProductStatus> findByUniqIds(List<String> uniqIds) {
        log.error("Fallback for findByUniqIds triggered for sku: {}", uniqIds, cause);
        if (cause instanceof FeignException.NotFound) {
            throw new ResponseStatusException(NOT_FOUND, "Products statuses not found for uniqIds: " + uniqIds, cause);
        }
        else if (cause instanceof CallNotPermittedException) {
            log.info("Circuit breaker pattern stopped from calling remote inventory-service: " + cause.getMessage());
            throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Circuit breaker pattern stopped from calling remote inventory-service", cause);        }
        throw new ResponseStatusException(SERVICE_UNAVAILABLE, "Service is unavailable, cannot retrieve products statuses for uniqIds: " + uniqIds, cause);
    }
}
