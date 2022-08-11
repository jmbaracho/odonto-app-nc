package br.com.jmb.odontoappnc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Atendimento.
 */
@Entity
@Table(name = "atendimento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Atendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data_atendimento", nullable = false)
    private LocalDate dataAtendimento;

    @ManyToOne
    private Procedimento procedimento;

    @ManyToOne
    @JsonIgnoreProperties(value = { "atendimentos" }, allowSetters = true)
    private Dentista dentista;

    @ManyToOne
    @JsonIgnoreProperties(value = { "atendimentos" }, allowSetters = true)
    private Paciente paciente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Atendimento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAtendimento() {
        return this.dataAtendimento;
    }

    public Atendimento dataAtendimento(LocalDate dataAtendimento) {
        this.setDataAtendimento(dataAtendimento);
        return this;
    }

    public void setDataAtendimento(LocalDate dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public Procedimento getProcedimento() {
        return this.procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public Atendimento procedimento(Procedimento procedimento) {
        this.setProcedimento(procedimento);
        return this;
    }

    public Dentista getDentista() {
        return this.dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public Atendimento dentista(Dentista dentista) {
        this.setDentista(dentista);
        return this;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Atendimento paciente(Paciente paciente) {
        this.setPaciente(paciente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Atendimento)) {
            return false;
        }
        return id != null && id.equals(((Atendimento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Atendimento{" +
            "id=" + getId() +
            ", dataAtendimento='" + getDataAtendimento() + "'" +
            "}";
    }
}
