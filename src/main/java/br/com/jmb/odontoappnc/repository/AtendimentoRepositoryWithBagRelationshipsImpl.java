package br.com.jmb.odontoappnc.repository;

import br.com.jmb.odontoappnc.domain.Atendimento;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AtendimentoRepositoryWithBagRelationshipsImpl implements AtendimentoRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Atendimento> fetchBagRelationships(Optional<Atendimento> atendimento) {
        return atendimento.map(this::fetchProcedimentos);
    }

    @Override
    public Page<Atendimento> fetchBagRelationships(Page<Atendimento> atendimentos) {
        return new PageImpl<>(
            fetchBagRelationships(atendimentos.getContent()),
            atendimentos.getPageable(),
            atendimentos.getTotalElements()
        );
    }

    @Override
    public List<Atendimento> fetchBagRelationships(List<Atendimento> atendimentos) {
        return Optional.of(atendimentos).map(this::fetchProcedimentos).orElse(Collections.emptyList());
    }

    Atendimento fetchProcedimentos(Atendimento result) {
        return entityManager
            .createQuery(
                "select atendimento from Atendimento atendimento left join fetch atendimento.procedimentos where atendimento is :atendimento",
                Atendimento.class
            )
            .setParameter("atendimento", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Atendimento> fetchProcedimentos(List<Atendimento> atendimentos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, atendimentos.size()).forEach(index -> order.put(atendimentos.get(index).getId(), index));
        List<Atendimento> result = entityManager
            .createQuery(
                "select distinct atendimento from Atendimento atendimento left join fetch atendimento.procedimentos where atendimento in :atendimentos",
                Atendimento.class
            )
            .setParameter("atendimentos", atendimentos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
