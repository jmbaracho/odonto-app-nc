package br.com.jmb.odontoappnc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jmb.odontoappnc.IntegrationTest;
import br.com.jmb.odontoappnc.domain.Atendimento;
import br.com.jmb.odontoappnc.repository.AtendimentoRepository;
import br.com.jmb.odontoappnc.service.AtendimentoService;
import br.com.jmb.odontoappnc.service.dto.AtendimentoDTO;
import br.com.jmb.odontoappnc.service.mapper.AtendimentoMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AtendimentoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AtendimentoResourceIT {

    private static final LocalDate DEFAULT_DATA_ATENDIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ATENDIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/atendimentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Mock
    private AtendimentoRepository atendimentoRepositoryMock;

    @Autowired
    private AtendimentoMapper atendimentoMapper;

    @Mock
    private AtendimentoService atendimentoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAtendimentoMockMvc;

    private Atendimento atendimento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Atendimento createEntity(EntityManager em) {
        Atendimento atendimento = new Atendimento().dataAtendimento(DEFAULT_DATA_ATENDIMENTO);
        return atendimento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Atendimento createUpdatedEntity(EntityManager em) {
        Atendimento atendimento = new Atendimento().dataAtendimento(UPDATED_DATA_ATENDIMENTO);
        return atendimento;
    }

    @BeforeEach
    public void initTest() {
        atendimento = createEntity(em);
    }

    @Test
    @Transactional
    void createAtendimento() throws Exception {
        int databaseSizeBeforeCreate = atendimentoRepository.findAll().size();
        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);
        restAtendimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(atendimentoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Atendimento testAtendimento = atendimentoList.get(atendimentoList.size() - 1);
        assertThat(testAtendimento.getDataAtendimento()).isEqualTo(DEFAULT_DATA_ATENDIMENTO);
    }

    @Test
    @Transactional
    void createAtendimentoWithExistingId() throws Exception {
        // Create the Atendimento with an existing ID
        atendimento.setId(1L);
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);

        int databaseSizeBeforeCreate = atendimentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtendimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(atendimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataAtendimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = atendimentoRepository.findAll().size();
        // set the field null
        atendimento.setDataAtendimento(null);

        // Create the Atendimento, which fails.
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);

        restAtendimentoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(atendimentoDTO))
            )
            .andExpect(status().isBadRequest());

        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAtendimentos() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList
        restAtendimentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAtendimento").value(hasItem(DEFAULT_DATA_ATENDIMENTO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAtendimentosWithEagerRelationshipsIsEnabled() throws Exception {
        when(atendimentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAtendimentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(atendimentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAtendimentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(atendimentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAtendimentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(atendimentoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAtendimento() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get the atendimento
        restAtendimentoMockMvc
            .perform(get(ENTITY_API_URL_ID, atendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(atendimento.getId().intValue()))
            .andExpect(jsonPath("$.dataAtendimento").value(DEFAULT_DATA_ATENDIMENTO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAtendimento() throws Exception {
        // Get the atendimento
        restAtendimentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAtendimento() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();

        // Update the atendimento
        Atendimento updatedAtendimento = atendimentoRepository.findById(atendimento.getId()).get();
        // Disconnect from session so that the updates on updatedAtendimento are not directly saved in db
        em.detach(updatedAtendimento);
        updatedAtendimento.dataAtendimento(UPDATED_DATA_ATENDIMENTO);
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(updatedAtendimento);

        restAtendimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, atendimentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
        Atendimento testAtendimento = atendimentoList.get(atendimentoList.size() - 1);
        assertThat(testAtendimento.getDataAtendimento()).isEqualTo(UPDATED_DATA_ATENDIMENTO);
    }

    @Test
    @Transactional
    void putNonExistingAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();
        atendimento.setId(count.incrementAndGet());

        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtendimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, atendimentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();
        atendimento.setId(count.incrementAndGet());

        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtendimentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();
        atendimento.setId(count.incrementAndGet());

        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtendimentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAtendimentoWithPatch() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();

        // Update the atendimento using partial update
        Atendimento partialUpdatedAtendimento = new Atendimento();
        partialUpdatedAtendimento.setId(atendimento.getId());

        restAtendimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtendimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAtendimento))
            )
            .andExpect(status().isOk());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
        Atendimento testAtendimento = atendimentoList.get(atendimentoList.size() - 1);
        assertThat(testAtendimento.getDataAtendimento()).isEqualTo(DEFAULT_DATA_ATENDIMENTO);
    }

    @Test
    @Transactional
    void fullUpdateAtendimentoWithPatch() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();

        // Update the atendimento using partial update
        Atendimento partialUpdatedAtendimento = new Atendimento();
        partialUpdatedAtendimento.setId(atendimento.getId());

        partialUpdatedAtendimento.dataAtendimento(UPDATED_DATA_ATENDIMENTO);

        restAtendimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtendimento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAtendimento))
            )
            .andExpect(status().isOk());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
        Atendimento testAtendimento = atendimentoList.get(atendimentoList.size() - 1);
        assertThat(testAtendimento.getDataAtendimento()).isEqualTo(UPDATED_DATA_ATENDIMENTO);
    }

    @Test
    @Transactional
    void patchNonExistingAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();
        atendimento.setId(count.incrementAndGet());

        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtendimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, atendimentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();
        atendimento.setId(count.incrementAndGet());

        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtendimentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();
        atendimento.setId(count.incrementAndGet());

        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.toDto(atendimento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtendimentoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(atendimentoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAtendimento() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        int databaseSizeBeforeDelete = atendimentoRepository.findAll().size();

        // Delete the atendimento
        restAtendimentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, atendimento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
