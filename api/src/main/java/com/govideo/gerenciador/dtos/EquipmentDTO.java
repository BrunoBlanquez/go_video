package com.govideo.gerenciador.dtos;

import com.govideo.gerenciador.entities.Equipment;
import com.govideo.gerenciador.entities.enuns.StatusEquipment;
import org.springframework.data.domain.Page;

public class EquipmentDTO {

    private Long id;

    private String modelo;

    private String descricao;

    private String marca;

    private String categoria;

    private String urlFoto;

    private StatusEquipment status;

    public EquipmentDTO(Equipment equipment) {
        this.id = equipment.getId();
        this.modelo = equipment.getModel().toString();
        this.descricao = equipment.getDescription().toString();
        this.marca = equipment.getBrand().toString();
        this.categoria = equipment.getCategory().toString();
        this.urlFoto = equipment.getImageURL().toString();
        this.status = equipment.getStatus();
    }

    public Long getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getMarca() {
        return marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public StatusEquipment getStatus() {
        return status;
    }

    public static Page<EquipmentDTO> convertToDTO(Page<Equipment> equipamentos) {
        return equipamentos.map(EquipmentDTO::new);
    }

}
