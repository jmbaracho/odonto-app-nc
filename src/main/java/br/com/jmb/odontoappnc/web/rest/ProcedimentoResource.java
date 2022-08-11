package br.com.jmb.odontoappnc.web.rest;

import br.com.jmb.odontoappnc.repository.ProcedimentoRepository;
import br.com.jmb.odontoappnc.service.ProcedimentoService;
import br.com.jmb.odontoappnc.service.dto.ProcedimentoDTO;
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
 * REST controller for managing {@link br.com.jmb.odontoappnc.domain.Procedimento}.
 */
@RestController
@RequestMapping("/api")
public class ProcedimentoResource {

    private final Logger log = LoggerFactory.getLogger(ProcedimentoResource.class);

    private static final String ENTITY_NAME = "procedimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcedimentoService procedimentoService;

    private final ProcedimentoRepository procedimentoRepository;

    public ProcedimentoResource(ProcedimentoService procedimentoService, ProcedimentoRepository procedimentoRepository) {
        this.procedimentoService = procedimentoService;
        this.procedimentoRepository = procedimentoRepository;
    }

    /**
     * {@code POST  /procedimentos} : Create a new procedimento.
     *
     * @param procedimentoDTO the procedimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new procedimentoDTO, or with status {@code 400 (Bad Request)} if the procedimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/procedimentos")
    public ResponseEntity<ProcedimentoDTO> createProcedimento(@Valid @RequestBody ProcedimentoDTO procedimentoDTO)
        throws URISyntaxException {
        log.debug("REST request to save Procedimento : {}", procedimentoDTO);
        if (procedimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new procedimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcedimentoDTO result = procedimentoService.save(procedimentoDTO);
        return ResponseEntity
            .created(new URI("/api/procedimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /procedimentos/:id} : Updates an existing procedimento.
     *
     * @param id the id of the procedimentoDTO to save.
     * @param procedimentoDTO the procedimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procedimentoDTO,
     * or with status {@code 400 (Bad Request)} if the procedimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the procedimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/procedimentos/{id}")
    public ResponseEntity<ProcedimentoDTO> updateProcedimento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProcedimentoDTO procedimentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Procedimento : {}, {}", id, procedimentoDTO);
        if (procedimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procedimentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procedimentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcedimentoDTO result = procedimentoService.update(procedimentoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, procedimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /procedimentos/:id} : Partial updates given fields of an existing procedimento, field will ignore if it is null
     *
     * @param id the id of the procedimentoDTO to save.
     * @param procedimentoDTO the procedimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procedimentoDTO,
     * or with status {@code 400 (Bad Request)} if the procedimentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the procedimentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the procedimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/procedimentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProcedimentoDTO> partialUpdateProcedimento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProcedimentoDTO procedimentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Procedimento partially : {}, {}", id, procedimentoDTO);
        if (procedimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procedimentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procedimentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcedimentoDTO> result = procedimentoService.partialUpdate(procedimentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, procedimentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /procedimentos} : get all the procedimentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of procedimentos in body.
     */
    @GetMapping("/procedimentos")
    public ResponseEntity<List<ProcedimentoDTO>> getAllProcedimentos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Procedimentos");
        Page<ProcedimentoDTO> page = procedimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /procedimentos/:id} : get the "id" procedimento.
     *
     * @param id the id of the procedimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the procedimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/procedimentos/{id}")
    public ResponseEntity<ProcedimentoDTO> getProcedimento(@PathVariable Long id) {
        log.debug("REST request to get Procedimento : {}", id);
        Optional<ProcedimentoDTO> procedimentoDTO = procedimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(procedimentoDTO);
    }

    /**
     * {@code DELETE  /procedimentos/:id} : delete the "id" procedimento.
     *
     * @param id the id of the procedimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/procedimentos/{id}")
    public ResponseEntity<Void> deleteProcedimento(@PathVariable Long id) {
        log.debug("REST request to delete Procedimento : {}", id);
        procedimentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
