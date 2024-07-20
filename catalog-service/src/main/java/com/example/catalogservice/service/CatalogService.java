package com.example.catalogservice.service;

import com.example.catalogservice.model.Product;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CatalogService {
    private List<Product> products = new ArrayList<>();
    final String filePath = "input/jcpenney_com-ecommerce_sample.csv";
    @PostConstruct
    public void loadProductsFromCSV() {
        log.info("Loading products from csv file: " + Paths.get(filePath).toAbsolutePath());
        try (Reader reader = new InputStreamReader(new ClassPathResource(filePath).getInputStream())) {
            CsvToBean<Product> csvToBean = new CsvToBeanBuilder<Product>(reader)
                    .withType(Product.class)
                    .withThrowExceptions(false)
                    .build();

            csvToBean.iterator().forEachRemaining(product -> {
                try {
                    products.add(product);
                } catch (Exception e) {
                    log.error("Error occur while adding products: " + e.getMessage());
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("Number of loaded products: " + products.size());
    }

    public Flux<Product> getProducts() {
        return Flux.fromIterable(products);
    }

    public Mono<Product> findProductByUniqId(String uniqId){
        return Mono.just(
                products
                        .stream()
                        .filter(s -> s.getUniqId().equals(uniqId))
                        .findFirst()
                        .orElseThrow(() ->  new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Not found product by uniq_id: " + uniqId)
                        )
        );
    }

    public Flux<Product> findProductBySku(String sku){
        return Flux.fromIterable(
                products
                        .stream()
                        .filter(s -> s.getSku().equals(sku))
                        .toList()
        );
    }
}
