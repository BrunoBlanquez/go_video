package com.govideo.gerenciador.valueobjects;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Model {
    private String valor;

    protected Model() {}

    public Model(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;
        Model model = (Model) o;
        return Objects.equals(valor, model.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
