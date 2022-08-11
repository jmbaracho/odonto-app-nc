package br.com.jmb.odontoappnc.service;

import br.com.jmb.odontoappnc.service.dto.AtendimentoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.jmb.odontoappnc.domain.Atendimento}.
 */
public interface AtendimentoService {
    /**
     * Save a atendimento.
     *
     * @param atendimentoDTO the entity to save.
     * @return the persisted entity.
     */
    AtendimentoDTO save(AtendimentoDTO atendimentoDTO);

    /**
     * Updates a atendimento.
     *
     * @param atendimentoDTO the entity to update.
     * @return the persisted entity.
     */
    AtendimentoDTO update(AtendimentoDTO atendimentoDTO);

    /**
     * Partially updates a atendimento.
     *
     * @param atendimentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AtendimentoDTO> partialUpdate(AtendimentoDTO atendimentoDTO);

    /**
     * Get all the atendimentos.
     *
     * @return the list of entities.
     */
    List<AtendimentoDTO> findAll();

    /**
     * Get all the atendimentos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AtendimentoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" atendimento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AtendimentoDTO> findOne(Long id);

    /**
     * Delete the "id" atendimento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
