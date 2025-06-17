package com.govideo.gerenciador.forms;

import com.govideo.gerenciador.entities.Equipment;
import com.govideo.gerenciador.valueobjects.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EquipmentForm {

    @NotNull
    @NotEmpty
    private String model;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    private String brand;

    @NotNull
    @NotEmpty
    private String category;

    @NotNull
    @NotEmpty
    private String imageURL;

    public EquipmentForm() {

    }

    public EquipmentForm(String model, String description, String brand, String category, String imageURL) {
        this.model = model;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.imageURL = imageURL;
    }

    public String getModel() {
        return model;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Equipment toDomain() {
        return new Equipment(new Model(model), new Description(description), new Brand(brand), new Category(category), new ImageURL(imageURL));
    }
}
