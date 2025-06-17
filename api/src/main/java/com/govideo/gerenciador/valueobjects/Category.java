package com.govideo.gerenciador.valueobjects;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Category {
    private String category;

    protected Category() {};

    public Category(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        this.category = value;
    }

    @Override
    public String toString() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;
        Category brand = (Category) o;
        return Objects.equals(category, brand.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }
}
