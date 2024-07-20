package com.example.productservice.client;

import com.example.productservice.model.Product;
import com.example.productservice.model.ProductStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CatalogClientTest {
    @Autowired
    private CatalogClient catalogClient;

    @Test
    void findProductByUniqId() {
        final String uniqId = "93e5272c51d8cce02597e3ce67b7ad0a";
        final Product expectedProduct = Product
                .builder()
                .uniqId(uniqId)
                .build();

        final Product actualProduct = catalogClient.findByUniqId(uniqId);

        assertEquals(expectedProduct, actualProduct);
    }

}