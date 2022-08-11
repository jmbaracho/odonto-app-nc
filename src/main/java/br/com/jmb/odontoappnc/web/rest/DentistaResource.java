package br.com.jmb.odontoappnc.web.rest;

import br.com.jmb.odontoappnc.repository.DentistaRepository;
import br.com.jmb.odontoappnc.service.DentistaService;
import br.com.jmb.odontoappnc.service.dto.DentistaDTO;
import br.com.jmb.odontoappnc.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.jmb.odontoappnc.domain.Dentista}.
 */
@RestController
@RequestMapping("/api")
public class DentistaResource {

    private final Logger log = LoggerFactory.getLogger(DentistaResource.class);

    private static final String ENTITY_NAME = "dentista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DentistaService dentistaService;

    private final DentistaRepository dentistaRepository;

    public DentistaResource(DentistaService dentistaService, DentistaRepository dentistaRepository) {
        this.dentistaService = dentistaService;
        this.dentistaRepository = dentistaRepository;
    }

    /**
     * {@code POST  /dentistas} : Create a new dentista.
     *
     * @param dentistaDTO the dentistaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dentistaDTO, or with status {@code 400 (Bad Request)} if the dentista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dentistas")
    public ResponseEntity<DentistaDTO> createDentista(@Valid @RequestBody DentistaDTO dentistaDTO) throws URISyntaxException {
        log.debug("REST request to save Dentista : {}", dentistaDTO);
        if (dentistaDTO.getId() != null) {
            throw new BadRequestAlertException("A new dentista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DentistaDTO result = dentistaService.save(dentistaDTO);
        return ResponseEntity
            .created(new URI("/api/dentistas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dentistas/:id} : Updates an existing dentista.
     *
     * @param id the id of the dentistaDTO to save.
     * @param dentistaDTO the dentistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dentistaDTO,
     * or with status {@code 400 (Bad Request)} if the dentistaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dentistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dentistas/{id}")
    public ResponseEntity<DentistaDTO> updateDentista(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DentistaDTO dentistaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Dentista : {}, {}", id, dentistaDTO);
        if (dentistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dentistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dentistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DentistaDTO result = dentistaService.update(dentistaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dentistaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dentistas/:id} : Partial updates given fields of an existing dentista, field will ignore if it is null
     *
     * @param id the id of the dentistaDTO to save.
     * @param dentistaDTO the dentistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dentistaDTO,
     * or with status {@code 400 (Bad Request)} if the dentistaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dentistaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dentistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dentistas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DentistaDTO> partialUpdateDentista(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DentistaDTO dentistaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dentista partially : {}, {}", id, dentistaDTO);
        if (dentistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dentistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dentistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DentistaDTO> result = dentistaService.partialUpdate(dentistaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dentistaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dentistas} : get all the dentistas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dentistas in body.
     */
    @GetMapping("/dentistas")
    public ResponseEntity<List<DentistaDTO>> getAllDentistas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Dentistas");
        Page<DentistaDTO> page = dentistaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dentistas/:id} : get the "id" dentista.
     *
     * @param id the id of the dentistaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dentistaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dentistas/{id}")
    public ResponseEntity<DentistaDTO> getDentista(@PathVariable Long id) {
        log.debug("REST request to get Dentista : {}", id);
        Optional<DentistaDTO> dentistaDTO = dentistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dentistaDTO);
    }

    /**
     * {@code DELETE  /dentistas/:id} : delete the "id" dentista.
     *
     * @param id the id of the dentistaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dentistas/{id}")
    public ResponseEntity<Void> deleteDentista(@PathVariable Long id) {
        log.debug("REST request to delete Dentista : {}", id);
        dentistaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
