package com.example.productservice.client;

import com.example.productservice.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Component
@Slf4j
public class CatalogClientFallbackFactory implements FallbackFactory<CatalogClientFallbackWithFactory> {

    @Override
    public CatalogClientFallbackWithFactory create(Throwable cause) {
        return new CatalogClientFallbackWithFactory(cause);
    }

}



