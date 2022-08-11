package br.com.jmb.odontoappnc.web.rest;

import br.com.jmb.odontoappnc.repository.AtendimentoRepository;
import br.com.jmb.odontoappnc.service.AtendimentoService;
import br.com.jmb.odontoappnc.service.dto.AtendimentoDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.jmb.odontoappnc.domain.Atendimento}.
 */
@RestController
@RequestMapping("/api")
public class AtendimentoResource {

    private final Logger log = LoggerFactory.getLogger(AtendimentoResource.class);

    private static final String ENTITY_NAME = "atendimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtendimentoService atendimentoService;

    private final AtendimentoRepository atendimentoRepository;

    public AtendimentoResource(AtendimentoService atendimentoService, AtendimentoRepository atendimentoRepository) {
        this.atendimentoService = atendimentoService;
        this.atendimentoRepository = atendimentoRepository;
    }

    /**
     * {@code POST  /atendimentos} : Create a new atendimento.
     *
     * @param atendimentoDTO the atendimentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new atendimentoDTO, or with status {@code 400 (Bad Request)} if the atendimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/atendimentos")
    public ResponseEntity<AtendimentoDTO> createAtendimento(@Valid @RequestBody AtendimentoDTO atendimentoDTO) throws URISyntaxException {
        log.debug("REST request to save Atendimento : {}", atendimentoDTO);
        if (atendimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new atendimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AtendimentoDTO result = atendimentoService.save(atendimentoDTO);
        return ResponseEntity
            .created(new URI("/api/atendimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /atendimentos/:id} : Updates an existing atendimento.
     *
     * @param id the id of the atendimentoDTO to save.
     * @param atendimentoDTO the atendimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atendimentoDTO,
     * or with status {@code 400 (Bad Request)} if the atendimentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the atendimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/atendimentos/{id}")
    public ResponseEntity<AtendimentoDTO> updateAtendimento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AtendimentoDTO atendimentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Atendimento : {}, {}", id, atendimentoDTO);
        if (atendimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, atendimentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!atendimentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AtendimentoDTO result = atendimentoService.update(atendimentoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, atendimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /atendimentos/:id} : Partial updates given fields of an existing atendimento, field will ignore if it is null
     *
     * @param id the id of the atendimentoDTO to save.
     * @param atendimentoDTO the atendimentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atendimentoDTO,
     * or with status {@code 400 (Bad Request)} if the atendimentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the atendimentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the atendimentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/atendimentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AtendimentoDTO> partialUpdateAtendimento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AtendimentoDTO atendimentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Atendimento partially : {}, {}", id, atendimentoDTO);
        if (atendimentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, atendimentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!atendimentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AtendimentoDTO> result = atendimentoService.partialUpdate(atendimentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, atendimentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /atendimentos} : get all the atendimentos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of atendimentos in body.
     */
    @GetMapping("/atendimentos")
    public List<AtendimentoDTO> getAllAtendimentos(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Atendimentos");
        return atendimentoService.findAll();
    }

    /**
     * {@code GET  /atendimentos/:id} : get the "id" atendimento.
     *
     * @param id the id of the atendimentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the atendimentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/atendimentos/{id}")
    public ResponseEntity<AtendimentoDTO> getAtendimento(@PathVariable Long id) {
        log.debug("REST request to get Atendimento : {}", id);
        Optional<AtendimentoDTO> atendimentoDTO = atendimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(atendimentoDTO);
    }

    /**
     * {@code DELETE  /atendimentos/:id} : delete the "id" atendimento.
     *
     * @param id the id of the atendimentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/atendimentos/{id}")
    public ResponseEntity<Void> deleteAtendimento(@PathVariable Long id) {
        log.debug("REST request to delete Atendimento : {}", id);
        atendimentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
