package br.com.jmb.odontoappnc.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.jmb.odontoappnc.domain.Procedimento} entity.
 */
public class ProcedimentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private BigDecimal valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcedimentoDTO)) {
            return false;
        }

        ProcedimentoDTO procedimentoDTO = (ProcedimentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, procedimentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcedimentoDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
