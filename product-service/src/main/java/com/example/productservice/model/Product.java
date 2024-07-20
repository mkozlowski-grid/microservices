package com.example.productservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private String uniqId;

    private String sku;

    private String nameTitle;

    private String description;

    private String listPrice;

    private String salePrice;

    private String category;

    private String categoryTree;

    private String averageProductRating;

    private String productUrl;

    private String productImageUrls;

    private String brand;

    private Long totalNumberReviews;

    private String reviews;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(uniqId, product.uniqId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqId);
    }
}
