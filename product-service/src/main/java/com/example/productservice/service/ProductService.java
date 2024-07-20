package com.example.productservice.service;

import com.example.productservice.client.CatalogClient;
import com.example.productservice.client.InventoryClient;
import com.example.productservice.model.Product;
import com.example.productservice.model.ProductStatus;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final CatalogClient catalogClient;
    private final InventoryClient inventoryClient;

    public Product findAvailableProductByUniqId(String uniqId){
        final List<ProductStatus> productStatus = findProductStatusByUniqIds(List.of(uniqId));

        final ProductStatus foundProductStatus = productStatus
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Not found product with uniq_id: " + uniqId + " in inventory."));

        if(!foundProductStatus.isAvailable()){
            throw new NoSuchElementException("Product with uniq_id: " + uniqId + " is not available in inventory.");
        }

        return findProductByUniqId(uniqId);
    }

    public List<Product> findAvailableProductsBySkus(String sku){
        final List<Product> products = findProductBySku(sku);
        final List<ProductStatus> availableProductStatuses = findProductStatusByUniqIds(products.stream().map(Product::getUniqId).toList())
                .stream()
                .filter(ProductStatus::isAvailable)
                .toList();

        return products
                .stream()
                .filter(s->availableProductStatuses.stream().map(ProductStatus::getProductUniqId).toList().contains(s.getUniqId()))
                .toList();
    }

    private Product findProductByUniqId(String uniqId){

            final Product product = catalogClient.findByUniqId(uniqId);
            if (Objects.isNull(product)) {
                log.info("Product with uniq_id: " + uniqId + "not found.");
            }
            log.info("Product with uniq_id: " + uniqId + ", data: " + product.toString());
            return product;

    }
    private Product fallback(String uniqId, Exception e) {
        throw new ResponseStatusException(NOT_FOUND, " EEEEXCPETION Unable to find status of product with id: " + uniqId);
    }


//    private Product findProductByUniqId(String uniqId){
//        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
//        try {
//            final Product product = catalogClient.findByUniqId(uniqId);
//            if (Objects.isNull(product)) {
//                log.info("Product with uniq_id: " + uniqId + "not found.");
//            }
//            log.info("Product with uniq_id: " + uniqId + ", data: " + product.toString());
//            return product;
//        }catch (FeignException e){
//            throw new ResponseStatusException(NOT_FOUND, "Unable to find status of product with id: " + uniqId);
//        }
//    }
    private List<Product> findProductBySku(String sku){
        final List<Product> products = catalogClient.findBySku(sku);
        log.info("Products with sku: " + sku + ", data: " + products);
        return products;
    }

    private List<ProductStatus> findProductStatusByUniqIds(List<String> uniqIds){
        List<ProductStatus> productsStatus = inventoryClient.findByUniqIds(uniqIds);
        log.info(productsStatus.toString());
        return productsStatus;
    }

    private boolean isProductAvailable(ProductStatus status){
        return status.isAvailable();
    }
}
