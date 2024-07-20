package com.example.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatus {
    private boolean isAvailable;
    private String productUniqId;
    private LocalDateTime checkedAt;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductStatus status = (ProductStatus) o;
        return isAvailable == status.isAvailable &&
                Objects.equals(productUniqId, status.productUniqId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAvailable, productUniqId);
    }
}
