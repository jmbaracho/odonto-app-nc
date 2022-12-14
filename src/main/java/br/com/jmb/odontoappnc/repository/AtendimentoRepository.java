package br.com.jmb.odontoappnc.repository;

import br.com.jmb.odontoappnc.domain.Atendimento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Atendimento entity.
 *
 * When extending this class, extend AtendimentoRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface AtendimentoRepository extends AtendimentoRepositoryWithBagRelationships, JpaRepository<Atendimento, Long> {
    default Optional<Atendimento> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Atendimento> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Atendimento> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct atendimento from Atendimento atendimento left join fetch atendimento.dentista left join fetch atendimento.paciente",
        countQuery = "select count(distinct atendimento) from Atendimento atendimento"
    )
    Page<Atendimento> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct atendimento from Atendimento atendimento left join fetch atendimento.dentista left join fetch atendimento.paciente"
    )
    List<Atendimento> findAllWithToOneRelationships();

    @Query(
        "select atendimento from Atendimento atendimento left join fetch atendimento.dentista left join fetch atendimento.paciente where atendimento.id =:id"
    )
    Optional<Atendimento> findOneWithToOneRelationships(@Param("id") Long id);
}
