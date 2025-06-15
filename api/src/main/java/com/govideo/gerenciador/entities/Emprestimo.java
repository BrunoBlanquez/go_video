package com.govideo.gerenciador.entities;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Equipment equipment;

    @ManyToOne
    private Usuario usuario;

    private LocalDateTime dataInicio = LocalDateTime.now();

    private LocalDateTime dataFim;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant  criadoEm;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant  atualizadoEm;

    @PrePersist
    public void prePersit() {
        criadoEm = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        atualizadoEm = Instant.now();
    }

    public Emprestimo() {
    }

    public Emprestimo(Equipment equipment, Usuario usuario) {
        this.equipment = equipment;
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimo that = (Emprestimo) o;
        return Objects.equals(id, that.id) && Objects.equals(equipment, that.equipment) && Objects.equals(dataInicio, that.dataInicio) && Objects.equals(dataFim, that.dataFim) && Objects.equals(criadoEm, that.criadoEm) && Objects.equals(atualizadoEm, that.atualizadoEm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, equipment, dataInicio, dataFim, criadoEm, atualizadoEm);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipment getEquipamento() {
        return equipment;
    }

    public void setEquipamento(Equipment equipment) {
        this.equipment = equipment;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

}
