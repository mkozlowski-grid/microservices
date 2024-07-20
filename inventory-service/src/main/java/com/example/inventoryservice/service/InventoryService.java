package com.example.inventoryservice.service;

import com.example.inventoryservice.model.Status;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryService {
    /**
     * This method returns product availability based on the first character in its uniq id.
     * If it's a digit, then the product is available, else it's not.
     */
    public List<Status> getProductsStatusByUniqIds(List<String> uniqIds){
        return uniqIds
                .stream()
                .filter(s -> !Objects.isNull(s) && !Strings.isBlank(s))
                .map(s -> new Status(Character.isDigit(s.charAt(0)), s, LocalDateTime.now()))
                .toList();
    }
}
