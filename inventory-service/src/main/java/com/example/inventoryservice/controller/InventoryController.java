package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Status;
import com.example.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory-service")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<List<Status>> getStatusByUniqIds(@RequestBody List<String> uniqIds){
        return ResponseEntity.ok(inventoryService.getProductsStatusByUniqIds(uniqIds));
    }
}
