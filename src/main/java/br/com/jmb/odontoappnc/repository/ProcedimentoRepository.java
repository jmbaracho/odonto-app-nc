package br.com.jmb.odontoappnc.repository;

import br.com.jmb.odontoappnc.domain.Procedimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Procedimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {}
