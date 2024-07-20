package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryServiceTest {
    private InventoryService inventoryService;

    @BeforeEach
    public void setUp(){
        inventoryService = new InventoryService();
    }

    @Test
    public void getProductsStatusByUniqIds(){
        final String uniqId1 = "93e5272c51d8cce02597e3ce67b7ad0a";
        final String uniqId2 = "505e6633d81f2cb7400c0cfa0394c427";
        final String uniqId3 = "dc414a69fee993fd20d74a979dfea23d";

        final List<String> uniqIds = List.of(uniqId1, uniqId2, uniqId3);
        final List<Status> expectedStatusList = List.of(
                new Status(true, uniqId1, null),
                new Status(true, uniqId2, null),
                new Status(false, uniqId3, null)
                );

        final List<Status> actualStatusList = inventoryService.getProductsStatusByUniqIds(uniqIds);

        assertEquals(expectedStatusList, actualStatusList);
    }

    @Test
    public void getProductsStatusByUniqIds_NullInput(){
        final String uniqId1 = null;
        final String uniqId2 = null;
        final String uniqId3 = null;

        final List<String> uniqIds = Arrays.asList(uniqId1, uniqId2, uniqId3);
        final List<Status> expectedStatusList = Collections.emptyList();

        final List<Status> actualStatusList = inventoryService.getProductsStatusByUniqIds(uniqIds);

        assertEquals(expectedStatusList, actualStatusList);
    }


    @Test
    public void getProductsStatusByUniqIds_BlankInput(){
        final String uniqId1 = "";
        final String uniqId2 = "\t";
        final String uniqId3 = "              ";

        final List<String> uniqIds = List.of(uniqId1, uniqId2, uniqId3);
        final List<Status> expectedStatusList = Collections.emptyList();

        final List<Status> actualStatusList = inventoryService.getProductsStatusByUniqIds(uniqIds);

        assertEquals(expectedStatusList, actualStatusList);
    }

    @Test
    public void getProductsStatusByUniqIds_SomeBlankAndSomeNullInput(){
        final String uniqId1 = "93e5272c51d8cce02597e3ce67b7ad0a";
        final String uniqId2 = "";
        final String uniqId3 = null;

        final List<String> uniqIds = Arrays.asList(uniqId1, uniqId2, uniqId3);
        final List<Status> expectedStatusList = List.of(new Status(true, uniqId1, null));

        final List<Status> actualStatusList = inventoryService.getProductsStatusByUniqIds(uniqIds);

        assertEquals(expectedStatusList, actualStatusList);
    }


}