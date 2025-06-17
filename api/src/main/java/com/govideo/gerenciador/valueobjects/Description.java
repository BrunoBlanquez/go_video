package com.govideo.gerenciador.valueobjects;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Description {
    private String valor;

    protected Description() {}

    public Description(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
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
        Description description = (Description) o;
        return Objects.equals(valor, description.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
