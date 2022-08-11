package br.com.jmb.odontoappnc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Procedimento.
 */
@Entity
@Table(name = "procedimento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Procedimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "valor", precision = 21, scale = 2, nullable = false)
    private BigDecimal valor;

    @ManyToMany(mappedBy = "procedimentos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "procedimentos", "dentista", "paciente" }, allowSetters = true)
    private Set<Atendimento> atendimentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Procedimento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Procedimento descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public Procedimento valor(BigDecimal valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Set<Atendimento> getAtendimentos() {
        return this.atendimentos;
    }

    public void setAtendimentos(Set<Atendimento> atendimentos) {
        if (this.atendimentos != null) {
            this.atendimentos.forEach(i -> i.removeProcedimento(this));
        }
        if (atendimentos != null) {
            atendimentos.forEach(i -> i.addProcedimento(this));
        }
        this.atendimentos = atendimentos;
    }

    public Procedimento atendimentos(Set<Atendimento> atendimentos) {
        this.setAtendimentos(atendimentos);
        return this;
    }

    public Procedimento addAtendimento(Atendimento atendimento) {
        this.atendimentos.add(atendimento);
        atendimento.getProcedimentos().add(this);
        return this;
    }

    public Procedimento removeAtendimento(Atendimento atendimento) {
        this.atendimentos.remove(atendimento);
        atendimento.getProcedimentos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Procedimento)) {
            return false;
        }
        return id != null && id.equals(((Procedimento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Procedimento{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
