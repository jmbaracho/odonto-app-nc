package br.com.jmb.odontoappnc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome_paciente", nullable = false)
    private String nomePaciente;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotNull
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotNull
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @NotNull
    @Column(name = "uf", nullable = false)
    private String uf;

    @Column(name = "complemento")
    private String complemento;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @OneToMany(mappedBy = "paciente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "procedimentos", "dentista", "paciente" }, allowSetters = true)
    private Set<Atendimento> atendimentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paciente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePaciente() {
        return this.nomePaciente;
    }

    public Paciente nomePaciente(String nomePaciente) {
        this.setNomePaciente(nomePaciente);
        return this;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Paciente cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public Paciente dataNascimento(LocalDate dataNascimento) {
        this.setDataNascimento(dataNascimento);
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public Paciente logradouro(String logradouro) {
        this.setLogradouro(logradouro);
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return this.numero;
    }

    public Paciente numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return this.bairro;
    }

    public Paciente bairro(String bairro) {
        this.setBairro(bairro);
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return this.cidade;
    }

    public Paciente cidade(String cidade) {
        this.setCidade(cidade);
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return this.uf;
    }

    public Paciente uf(String uf) {
        this.setUf(uf);
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public Paciente complemento(String complemento) {
        this.setComplemento(complemento);
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEmail() {
        return this.email;
    }

    public Paciente email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Paciente telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Set<Atendimento> getAtendimentos() {
        return this.atendimentos;
    }

    public void setAtendimentos(Set<Atendimento> atendimentos) {
        if (this.atendimentos != null) {
            this.atendimentos.forEach(i -> i.setPaciente(null));
        }
        if (atendimentos != null) {
            atendimentos.forEach(i -> i.setPaciente(this));
        }
        this.atendimentos = atendimentos;
    }

    public Paciente atendimentos(Set<Atendimento> atendimentos) {
        this.setAtendimentos(atendimentos);
        return this;
    }

    public Paciente addAtendimento(Atendimento atendimento) {
        this.atendimentos.add(atendimento);
        atendimento.setPaciente(this);
        return this;
    }

    public Paciente removeAtendimento(Atendimento atendimento) {
        this.atendimentos.remove(atendimento);
        atendimento.setPaciente(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }
        return id != null && id.equals(((Paciente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", nomePaciente='" + getNomePaciente() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", uf='" + getUf() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            "}";
    }
}
