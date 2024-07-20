package com.example.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Status implements Comparable<Status>{
    private boolean isAvailable;
    private String productUniqId;
    private LocalDateTime checkedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return isAvailable == status.isAvailable &&
                Objects.equals(productUniqId, status.productUniqId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isAvailable, productUniqId);
    }

    @Override
    public int compareTo(Status o) {
        int availabilityComparison = Boolean.compare(this.isAvailable, o.isAvailable);
        if (availabilityComparison != 0) {
            return availabilityComparison;
        }
        return this.productUniqId.compareTo(o.productUniqId);
    }
}
