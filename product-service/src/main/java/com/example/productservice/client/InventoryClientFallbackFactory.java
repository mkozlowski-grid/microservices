package com.example.productservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryClientFallbackFactory  implements FallbackFactory<InventoryClientFallbackWithFactory> {
    @Override
    public InventoryClientFallbackWithFactory create(Throwable cause) {
        return new InventoryClientFallbackWithFactory(cause);
    }
}
