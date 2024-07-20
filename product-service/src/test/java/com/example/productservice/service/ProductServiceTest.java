package com.example.productservice.service;

import com.example.productservice.client.CatalogClient;
import com.example.productservice.client.InventoryClient;
import com.example.productservice.model.Product;
import com.example.productservice.model.ProductStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private InventoryClient inventoryClient;
    @Mock
    private CatalogClient catalogClient;

    @Test
    void findAvailableProductByUniqId() {
        final String uniqId = "93e5272c51d8cce02597e3ce67b7ad0a";
        final List<ProductStatus> expectedStatusList = List.of(
                new ProductStatus(true, uniqId, null)
        );
        final Product expectedProduct = Product
                .builder()
                .uniqId(uniqId)
                .build();
        final Product expectedAvailableProduct = expectedProduct;

        Mockito.when(inventoryClient.findByUniqIds(List.of(uniqId))).thenReturn(expectedStatusList);
        Mockito.when(catalogClient.findByUniqId(uniqId)).thenReturn(expectedProduct);

        final Product actualAvailableProduct = productService.findAvailableProductByUniqId(uniqId);

        assertEquals(expectedAvailableProduct, actualAvailableProduct);
    }

    @Test
    void findAvailableProductsBySkus() {
        final String sku = "pp5006380337";

        final String product1UniqId = "1";
        final String product2UniqId = "2";
        final String product3UniqId = "3";

        final Product product1 = Product.builder().uniqId(product1UniqId).sku(sku).build();
        final Product product2 = Product.builder().uniqId(product2UniqId).sku(sku).build();
        final Product product3 = Product.builder().uniqId(product3UniqId).sku(sku).build();

        final List<Product> expectedProducts = List.of(product1, product2, product3);
        final List<Product> expectedAvailableProducts = List.of(product1, product3);

        final ProductStatus product1Status = new ProductStatus(true, product1UniqId, null);
        final ProductStatus product2Status = new ProductStatus(false, product2UniqId, null);
        final ProductStatus product3Status = new ProductStatus(true, product3UniqId, null);



        Mockito.when(catalogClient.findBySku(sku)).thenReturn(expectedProducts);

        Mockito.when(inventoryClient.findByUniqIds(List.of(product1UniqId, product2UniqId, product3UniqId))).thenReturn(List.of(product1Status, product2Status, product3Status));

        final List<Product> actualAvailableProducts = productService.findAvailableProductsBySkus(sku);

        assertEquals(actualAvailableProducts, expectedAvailableProducts);
    }
}
/*
*         final String uniqId1 = "93e5272c51d8cce02597e3ce67b7ad0a";
        final String uniqId2 = "505e6633d81f2cb7400c0cfa0394c427";
        final String uniqId3 = "dc414a69fee993fd20d74a979dfea23d";

        final List<ProductStatus> expectedStatusList = List.of(
                new ProductStatus(true, uniqId1, null),
                new ProductStatus(true, uniqId2, null),
                new ProductStatus(false, uniqId3, null)
        );
        final List<String> uniqIds = List.of(uniqId1, uniqId2, uniqId3);

        List<ProductStatus> actualProductStatuses = inventoryClient.findByUniqIds(uniqIds);
        assertEquals(expectedStatusList, actualProductStatuses);*/