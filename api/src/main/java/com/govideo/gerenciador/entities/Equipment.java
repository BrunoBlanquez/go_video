package com.govideo.gerenciador.entities;

import com.govideo.gerenciador.entities.enuns.StatusEquipment;
import com.govideo.gerenciador.forms.EquipmentForm;
import com.govideo.gerenciador.valueobjects.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Model model;

    private Description description;

    private Brand brand;

    private Category category;

    private ImageURL imageURL;

    @Enumerated(EnumType.STRING)
    private StatusEquipment status = StatusEquipment.AVAILABLE;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant updatedAt;

    @PrePersist
    public void prePersit() {
        createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public Equipment() {

    }

    public Equipment(Model model, Description description, Brand brand, Category category, ImageURL imageURL) {
        this.model = model;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.imageURL = imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment that = (Equipment) o;
        return Objects.equals(id, that.id) && Objects.equals(model, that.model) && Objects.equals(description, that.description) && Objects.equals(brand, that.brand) && Objects.equals(category, that.category) && status == that.status;
    }

    public boolean isAvailable() {
        return this.status == StatusEquipment.AVAILABLE;
    }

    public Long getId() {
        return id;
    }

    public Model getModel() {
        return model;
    }

    public Description getDescription() {
        return description;
    }

    public Brand getBrand() {
        return brand;
    }

    public Category getCategory() {
        return category;
    }

    public ImageURL getImageURL() {
        return imageURL;
    }

    public StatusEquipment getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, description, brand, category, status);
    }

    public void updateWith(EquipmentForm form) {
        this.model = new Model(form.getModel());
        this.description = new Description(form.getDescription());
        this.brand = new Brand(form.getBrand());
        this.category = new Category(form.getCategory());
        this.imageURL = new ImageURL(form.getImageURL());
    }
}
