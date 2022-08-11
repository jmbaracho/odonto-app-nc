package br.com.jmb.odontoappnc.repository;

import br.com.jmb.odontoappnc.domain.Dentista;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Dentista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DentistaRepository extends JpaRepository<Dentista, Long> {}
