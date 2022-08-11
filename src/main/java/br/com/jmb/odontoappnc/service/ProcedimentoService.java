package br.com.jmb.odontoappnc.service;

import br.com.jmb.odontoappnc.service.dto.ProcedimentoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.jmb.odontoappnc.domain.Procedimento}.
 */
public interface ProcedimentoService {
    /**
     * Save a procedimento.
     *
     * @param procedimentoDTO the entity to save.
     * @return the persisted entity.
     */
    ProcedimentoDTO save(ProcedimentoDTO procedimentoDTO);

    /**
     * Updates a procedimento.
     *
     * @param procedimentoDTO the entity to update.
     * @return the persisted entity.
     */
    ProcedimentoDTO update(ProcedimentoDTO procedimentoDTO);

    /**
     * Partially updates a procedimento.
     *
     * @param procedimentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProcedimentoDTO> partialUpdate(ProcedimentoDTO procedimentoDTO);

    /**
     * Get all the procedimentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProcedimentoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" procedimento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProcedimentoDTO> findOne(Long id);

    /**
     * Delete the "id" procedimento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
