package com.example.catalogservice.service;

import com.example.catalogservice.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CatalogServiceIntegrationTest {

    @Autowired
    private CatalogService catalogService;

    @BeforeEach
    public void setUp() {}

    @Test
    public void getProducts() throws Exception {
            List<Product> actualProducts = catalogService.getProducts().collectList().block();
    }

    @Test
    public void loadProductsFromCSV() {
        catalogService.loadProductsFromCSV();
    }

}
