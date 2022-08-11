package br.com.jmb.odontoappnc.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.jmb.odontoappnc.domain.Atendimento} entity.
 */
public class AtendimentoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dataAtendimento;

    private Set<ProcedimentoDTO> procedimentos = new HashSet<>();

    private DentistaDTO dentista;

    private PacienteDTO paciente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDate dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public Set<ProcedimentoDTO> getProcedimentos() {
        return procedimentos;
    }

    public void setProcedimentos(Set<ProcedimentoDTO> procedimentos) {
        this.procedimentos = procedimentos;
    }

    public DentistaDTO getDentista() {
        return dentista;
    }

    public void setDentista(DentistaDTO dentista) {
        this.dentista = dentista;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtendimentoDTO)) {
            return false;
        }

        AtendimentoDTO atendimentoDTO = (AtendimentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, atendimentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AtendimentoDTO{" +
            "id=" + getId() +
            ", dataAtendimento='" + getDataAtendimento() + "'" +
            ", procedimentos=" + getProcedimentos() +
            ", dentista=" + getDentista() +
            ", paciente=" + getPaciente() +
            "}";
    }
}
