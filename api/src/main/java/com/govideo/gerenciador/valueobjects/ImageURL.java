package com.govideo.gerenciador.valueobjects;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ImageURL {
    private String imageURL;

    protected ImageURL() {}

    public ImageURL(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Image URL cannot be null or empty");
        }
        this.imageURL = value;
    }

    @Override
    public String toString() {
        return imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;
        ImageURL imageURL = (ImageURL) o;
        return Objects.equals(imageURL, imageURL.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageURL);
    }
}
