package br.com.jmb.odontoappnc.service;

import br.com.jmb.odontoappnc.service.dto.DentistaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.jmb.odontoappnc.domain.Dentista}.
 */
public interface DentistaService {
    /**
     * Save a dentista.
     *
     * @param dentistaDTO the entity to save.
     * @return the persisted entity.
     */
    DentistaDTO save(DentistaDTO dentistaDTO);

    /**
     * Updates a dentista.
     *
     * @param dentistaDTO the entity to update.
     * @return the persisted entity.
     */
    DentistaDTO update(DentistaDTO dentistaDTO);

    /**
     * Partially updates a dentista.
     *
     * @param dentistaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DentistaDTO> partialUpdate(DentistaDTO dentistaDTO);

    /**
     * Get all the dentistas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DentistaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dentista.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DentistaDTO> findOne(Long id);

    /**
     * Delete the "id" dentista.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
