package com.govideo.gerenciador.valueobjects;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Brand {
    private String brandValue;

    protected Brand() {}

    public Brand(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be null or empty");
        }
        this.brandValue = value;
    }

    @Override
    public String toString() {
        return brandValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;
        Brand brand = (Brand) o;
        return Objects.equals(brandValue, brand.brandValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandValue);
    }
}
