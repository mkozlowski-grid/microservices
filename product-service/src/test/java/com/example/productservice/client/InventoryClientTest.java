package com.example.productservice.client;

import com.example.productservice.model.Product;
import com.example.productservice.model.ProductStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InventoryClientTest {
    @Autowired
    private InventoryClient inventoryClient;

    @Test
    void findProductStatusByUniqIds(){
        final String uniqId1 = "93e5272c51d8cce02597e3ce67b7ad0a";
        final String uniqId2 = "505e6633d81f2cb7400c0cfa0394c427";
        final String uniqId3 = "dc414a69fee993fd20d74a979dfea23d";

        final List<ProductStatus> expectedStatusList = List.of(
                new ProductStatus(true, uniqId1, null),
                new ProductStatus(true, uniqId2, null),
                new ProductStatus(false, uniqId3, null)
        );
        final List<String> uniqIds = List.of(uniqId1, uniqId2, uniqId3);

        List<ProductStatus> actualProductStatuses = inventoryClient.findByUniqIds(uniqIds);
        assertEquals(expectedStatusList, actualProductStatuses);
    }
}