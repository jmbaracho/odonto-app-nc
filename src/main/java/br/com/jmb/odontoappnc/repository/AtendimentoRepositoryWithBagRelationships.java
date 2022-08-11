package br.com.jmb.odontoappnc.repository;

import br.com.jmb.odontoappnc.domain.Atendimento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface AtendimentoRepositoryWithBagRelationships {
    Optional<Atendimento> fetchBagRelationships(Optional<Atendimento> atendimento);

    List<Atendimento> fetchBagRelationships(List<Atendimento> atendimentos);

    Page<Atendimento> fetchBagRelationships(Page<Atendimento> atendimentos);
}
