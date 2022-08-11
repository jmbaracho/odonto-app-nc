package br.com.jmb.odontoappnc.web.rest;

import static br.com.jmb.odontoappnc.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jmb.odontoappnc.IntegrationTest;
import br.com.jmb.odontoappnc.domain.Procedimento;
import br.com.jmb.odontoappnc.repository.ProcedimentoRepository;
import br.com.jmb.odontoappnc.service.dto.ProcedimentoDTO;
import br.com.jmb.odontoappnc.service.mapper.ProcedimentoMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProcedimentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcedimentoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/procedimentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    @Autowired
    private ProcedimentoMapper procedimentoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcedimentoMockMvc;

    private Procedimento procedimento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procedimento createEntity(EntityManager em) {
        Procedimento procedimento = new Procedimento().descricao(DEFAULT_DESCRICAO).valor(DEFAULT_VALOR);
        return procedimento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procedimento createUpdatedEntity(EntityManager em) {
        Procedimento procedimento = new Procedimento().descricao(UPDATED_DESCRICAO).valor(UPDATED_VALOR);
        return procedimento;
    }

    @BeforeEach
    public void initTest() {
        procedimento = createEntity(em);
    }

    @Test
    @Transactional
    void createProcedimento() throws Exception {
        int databaseSizeBeforeCreate = procedimentoRepository.findAll().size();
        // Create the Procedimento
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);
        restProcedimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procedimentoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Procedimento testProcedimento = procedimentoList.get(procedimentoList.size() - 1);
        assertThat(testProcedimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProcedimento.getValor()).isEqualByComparingTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void createProcedimentoWithExistingId() throws Exception {
        // Create the Procedimento with an existing ID
        procedimento.setId(1L);
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        int databaseSizeBeforeCreate = procedimentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procedimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedimentoRepository.findAll().size();
        // set the field null
        procedimento.setDescricao(null);

        // Create the Procedimento, which fails.
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        restProcedimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procedimentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedimentoRepository.findAll().size();
        // set the field null
        procedimento.setValor(null);

        // Create the Procedimento, which fails.
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        restProcedimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procedimentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProcedimentos() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        // Get all the procedimentoList
        restProcedimentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(sameNumber(DEFAULT_VALOR))));
    }

    @Test
    @Transactional
    void getProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        // Get the procedimento
        restProcedimentoMockMvc
            .perform(get(ENTITY_API_URL_ID, procedimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(procedimento.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.valor").value(sameNumber(DEFAULT_VALOR)));
    }

    @Test
    @Transactional
    void getNonExistingProcedimento() throws Exception {
        // Get the procedimento
        restProcedimentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();

        // Update the procedimento
        Procedimento updatedProcedimento = procedimentoRepository.findById(procedimento.getId()).get();
        // Disconnect from session so that the updates on updatedProcedimento are not directly saved in db
        em.detach(updatedProcedimento);
        updatedProcedimento.descricao(UPDATED_DESCRICAO).valor(UPDATED_VALOR);
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(updatedProcedimento);

        restProcedimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, procedimentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
        Procedimento testProcedimento = procedimentoList.get(procedimentoList.size() - 1);
        assertThat(testProcedimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProcedimento.getValor()).isEqualByComparingTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void putNonExistingProcedimento() throws Exception {
        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();
        procedimento.setId(count.incrementAndGet());

        // Create the Procedimento
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcedimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, procedimentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcedimento() throws Exception {
        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();
        procedimento.setId(count.incrementAndGet());

        // Create the Procedimento
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcedimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcedimento() throws Exception {
        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();
        procedimento.setId(count.incrementAndGet());

        // Create the Procedimento
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcedimentoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procedimentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcedimentoWithPatch() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();

        // Update the procedimento using partial update
        Procedimento partialUpdatedProcedimento = new Procedimento();
        partialUpdatedProcedimento.setId(procedimento.getId());

        partialUpdatedProcedimento.descricao(UPDATED_DESCRICAO).valor(UPDATED_VALOR);

        restProcedimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcedimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcedimento))
            )
            .andExpect(status().isOk());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
        Procedimento testProcedimento = procedimentoList.get(procedimentoList.size() - 1);
        assertThat(testProcedimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProcedimento.getValor()).isEqualByComparingTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void fullUpdateProcedimentoWithPatch() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();

        // Update the procedimento using partial update
        Procedimento partialUpdatedProcedimento = new Procedimento();
        partialUpdatedProcedimento.setId(procedimento.getId());

        partialUpdatedProcedimento.descricao(UPDATED_DESCRICAO).valor(UPDATED_VALOR);

        restProcedimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcedimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcedimento))
            )
            .andExpect(status().isOk());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
        Procedimento testProcedimento = procedimentoList.get(procedimentoList.size() - 1);
        assertThat(testProcedimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProcedimento.getValor()).isEqualByComparingTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    void patchNonExistingProcedimento() throws Exception {
        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();
        procedimento.setId(count.incrementAndGet());

        // Create the Procedimento
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcedimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, procedimentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcedimento() throws Exception {
        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();
        procedimento.setId(count.incrementAndGet());

        // Create the Procedimento
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcedimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcedimento() throws Exception {
        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();
        procedimento.setId(count.incrementAndGet());

        // Create the Procedimento
        ProcedimentoDTO procedimentoDTO = procedimentoMapper.toDto(procedimento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcedimentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(procedimentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        int databaseSizeBeforeDelete = procedimentoRepository.findAll().size();

        // Delete the procedimento
        restProcedimentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, procedimento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
