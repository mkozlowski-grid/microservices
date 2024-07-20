package com.example.productservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import feign.codec.Decoder;
import feign.codec.Encoder;

import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
    @Bean
    public Decoder decoder() {
        return new JacksonDecoder(objectMapper());
    }

    @Bean
    public Encoder encoder() {
        return new JacksonEncoder(objectMapper());
    }

    @Bean
    public ObjectMapper objectMapper() {
        JsonMapper mapper = JsonMapper.builder() // or different mapper for other format
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                .build();
        return mapper;
    }
}

