package com.govideo.gerenciador.entities;

import com.govideo.gerenciador.entities.enuns.StatusEquipment;
import com.govideo.gerenciador.forms.EquipmentForm;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private String description;

    private String brand;

    private String category;

    private String imageURL;

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

    public Equipment(String model, String description, String brand, String category, String imageURL) {
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

    @Override
    public int hashCode() {
        return Objects.hash(id, model, description, brand, category, status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public StatusEquipment getStatus() {
        return status;
    }

    public void setStatus(StatusEquipment status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void updateWith(EquipmentForm form) {
        this.model = form.getModelo();
        this.description = form.getDescricao();
        this.brand = form.getMarca();
        this.category = form.getCategoria();
        this.imageURL = form.getUrlFoto();
    }
}
