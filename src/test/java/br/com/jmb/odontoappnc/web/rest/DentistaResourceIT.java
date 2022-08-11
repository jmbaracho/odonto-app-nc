package br.com.jmb.odontoappnc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jmb.odontoappnc.IntegrationTest;
import br.com.jmb.odontoappnc.domain.Dentista;
import br.com.jmb.odontoappnc.repository.DentistaRepository;
import br.com.jmb.odontoappnc.service.dto.DentistaDTO;
import br.com.jmb.odontoappnc.service.mapper.DentistaMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link DentistaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DentistaResourceIT {

    private static final String DEFAULT_NOME_DENTISTA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DENTISTA = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_CRO = "AAAAAAAAAA";
    private static final String UPDATED_CRO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_UF = "AAAAAAAAAA";
    private static final String UPDATED_UF = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dentistas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private DentistaMapper dentistaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDentistaMockMvc;

    private Dentista dentista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dentista createEntity(EntityManager em) {
        Dentista dentista = new Dentista()
            .nomeDentista(DEFAULT_NOME_DENTISTA)
            .cpf(DEFAULT_CPF)
            .cro(DEFAULT_CRO)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .bairro(DEFAULT_BAIRRO)
            .cidade(DEFAULT_CIDADE)
            .uf(DEFAULT_UF)
            .complemento(DEFAULT_COMPLEMENTO)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE);
        return dentista;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dentista createUpdatedEntity(EntityManager em) {
        Dentista dentista = new Dentista()
            .nomeDentista(UPDATED_NOME_DENTISTA)
            .cpf(UPDATED_CPF)
            .cro(UPDATED_CRO)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .uf(UPDATED_UF)
            .complemento(UPDATED_COMPLEMENTO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);
        return dentista;
    }

    @BeforeEach
    public void initTest() {
        dentista = createEntity(em);
    }

    @Test
    @Transactional
    void createDentista() throws Exception {
        int databaseSizeBeforeCreate = dentistaRepository.findAll().size();
        // Create the Dentista
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);
        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isCreated());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeCreate + 1);
        Dentista testDentista = dentistaList.get(dentistaList.size() - 1);
        assertThat(testDentista.getNomeDentista()).isEqualTo(DEFAULT_NOME_DENTISTA);
        assertThat(testDentista.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testDentista.getCro()).isEqualTo(DEFAULT_CRO);
        assertThat(testDentista.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testDentista.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testDentista.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testDentista.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testDentista.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testDentista.getUf()).isEqualTo(DEFAULT_UF);
        assertThat(testDentista.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testDentista.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDentista.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    void createDentistaWithExistingId() throws Exception {
        // Create the Dentista with an existing ID
        dentista.setId(1L);
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        int databaseSizeBeforeCreate = dentistaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeDentistaIsRequired() throws Exception {
        int databaseSizeBeforeTest = dentistaRepository.findAll().size();
        // set the field null
        dentista.setNomeDentista(null);

        // Create the Dentista, which fails.
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = dentistaRepository.findAll().size();
        // set the field null
        dentista.setCpf(null);

        // Create the Dentista, which fails.
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCroIsRequired() throws Exception {
        int databaseSizeBeforeTest = dentistaRepository.findAll().size();
        // set the field null
        dentista.setCro(null);

        // Create the Dentista, which fails.
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dentistaRepository.findAll().size();
        // set the field null
        dentista.setDataNascimento(null);

        // Create the Dentista, which fails.
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLogradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = dentistaRepository.findAll().size();
        // set the field null
        dentista.setLogradouro(null);

        // Create the Dentista, which fails.
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = dentistaRepository.findAll().size();
        // set the field null
        dentista.setNumero(null);

        // Create the Dentista, which fails.
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = dentistaRepository.findAll().size();
        // set the field null
        dentista.setBairro(null);

        // Create the Dentista, which fails.
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dentistaRepository.findAll().size();
        // set the field null
        dentista.setCidade(null);

        // Create the Dentista, which fails.
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUfIsRequired() throws Exception {
        int databaseSizeBeforeTest = dentistaRepository.findAll().size();
        // set the field null
        dentista.setUf(null);

        // Create the Dentista, which fails.
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = dentistaRepository.findAll().size();
        // set the field null
        dentista.setEmail(null);

        // Create the Dentista, which fails.
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = dentistaRepository.findAll().size();
        // set the field null
        dentista.setTelefone(null);

        // Create the Dentista, which fails.
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        restDentistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isBadRequest());

        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDentistas() throws Exception {
        // Initialize the database
        dentistaRepository.saveAndFlush(dentista);

        // Get all the dentistaList
        restDentistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dentista.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDentista").value(hasItem(DEFAULT_NOME_DENTISTA)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].cro").value(hasItem(DEFAULT_CRO)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)));
    }

    @Test
    @Transactional
    void getDentista() throws Exception {
        // Initialize the database
        dentistaRepository.saveAndFlush(dentista);

        // Get the dentista
        restDentistaMockMvc
            .perform(get(ENTITY_API_URL_ID, dentista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dentista.getId().intValue()))
            .andExpect(jsonPath("$.nomeDentista").value(DEFAULT_NOME_DENTISTA))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.cro").value(DEFAULT_CRO))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.uf").value(DEFAULT_UF))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE));
    }

    @Test
    @Transactional
    void getNonExistingDentista() throws Exception {
        // Get the dentista
        restDentistaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDentista() throws Exception {
        // Initialize the database
        dentistaRepository.saveAndFlush(dentista);

        int databaseSizeBeforeUpdate = dentistaRepository.findAll().size();

        // Update the dentista
        Dentista updatedDentista = dentistaRepository.findById(dentista.getId()).get();
        // Disconnect from session so that the updates on updatedDentista are not directly saved in db
        em.detach(updatedDentista);
        updatedDentista
            .nomeDentista(UPDATED_NOME_DENTISTA)
            .cpf(UPDATED_CPF)
            .cro(UPDATED_CRO)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .uf(UPDATED_UF)
            .complemento(UPDATED_COMPLEMENTO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);
        DentistaDTO dentistaDTO = dentistaMapper.toDto(updatedDentista);

        restDentistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dentistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dentistaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeUpdate);
        Dentista testDentista = dentistaList.get(dentistaList.size() - 1);
        assertThat(testDentista.getNomeDentista()).isEqualTo(UPDATED_NOME_DENTISTA);
        assertThat(testDentista.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testDentista.getCro()).isEqualTo(UPDATED_CRO);
        assertThat(testDentista.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testDentista.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testDentista.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDentista.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testDentista.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testDentista.getUf()).isEqualTo(UPDATED_UF);
        assertThat(testDentista.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testDentista.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDentista.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void putNonExistingDentista() throws Exception {
        int databaseSizeBeforeUpdate = dentistaRepository.findAll().size();
        dentista.setId(count.incrementAndGet());

        // Create the Dentista
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDentistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dentistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dentistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDentista() throws Exception {
        int databaseSizeBeforeUpdate = dentistaRepository.findAll().size();
        dentista.setId(count.incrementAndGet());

        // Create the Dentista
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDentistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dentistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDentista() throws Exception {
        int databaseSizeBeforeUpdate = dentistaRepository.findAll().size();
        dentista.setId(count.incrementAndGet());

        // Create the Dentista
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDentistaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dentistaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDentistaWithPatch() throws Exception {
        // Initialize the database
        dentistaRepository.saveAndFlush(dentista);

        int databaseSizeBeforeUpdate = dentistaRepository.findAll().size();

        // Update the dentista using partial update
        Dentista partialUpdatedDentista = new Dentista();
        partialUpdatedDentista.setId(dentista.getId());

        partialUpdatedDentista
            .cpf(UPDATED_CPF)
            .logradouro(UPDATED_LOGRADOURO)
            .complemento(UPDATED_COMPLEMENTO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);

        restDentistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDentista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDentista))
            )
            .andExpect(status().isOk());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeUpdate);
        Dentista testDentista = dentistaList.get(dentistaList.size() - 1);
        assertThat(testDentista.getNomeDentista()).isEqualTo(DEFAULT_NOME_DENTISTA);
        assertThat(testDentista.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testDentista.getCro()).isEqualTo(DEFAULT_CRO);
        assertThat(testDentista.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testDentista.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testDentista.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testDentista.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testDentista.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testDentista.getUf()).isEqualTo(DEFAULT_UF);
        assertThat(testDentista.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testDentista.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDentista.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void fullUpdateDentistaWithPatch() throws Exception {
        // Initialize the database
        dentistaRepository.saveAndFlush(dentista);

        int databaseSizeBeforeUpdate = dentistaRepository.findAll().size();

        // Update the dentista using partial update
        Dentista partialUpdatedDentista = new Dentista();
        partialUpdatedDentista.setId(dentista.getId());

        partialUpdatedDentista
            .nomeDentista(UPDATED_NOME_DENTISTA)
            .cpf(UPDATED_CPF)
            .cro(UPDATED_CRO)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .uf(UPDATED_UF)
            .complemento(UPDATED_COMPLEMENTO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);

        restDentistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDentista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDentista))
            )
            .andExpect(status().isOk());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeUpdate);
        Dentista testDentista = dentistaList.get(dentistaList.size() - 1);
        assertThat(testDentista.getNomeDentista()).isEqualTo(UPDATED_NOME_DENTISTA);
        assertThat(testDentista.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testDentista.getCro()).isEqualTo(UPDATED_CRO);
        assertThat(testDentista.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testDentista.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testDentista.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testDentista.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testDentista.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testDentista.getUf()).isEqualTo(UPDATED_UF);
        assertThat(testDentista.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testDentista.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDentista.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void patchNonExistingDentista() throws Exception {
        int databaseSizeBeforeUpdate = dentistaRepository.findAll().size();
        dentista.setId(count.incrementAndGet());

        // Create the Dentista
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDentistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dentistaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dentistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDentista() throws Exception {
        int databaseSizeBeforeUpdate = dentistaRepository.findAll().size();
        dentista.setId(count.incrementAndGet());

        // Create the Dentista
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDentistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dentistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDentista() throws Exception {
        int databaseSizeBeforeUpdate = dentistaRepository.findAll().size();
        dentista.setId(count.incrementAndGet());

        // Create the Dentista
        DentistaDTO dentistaDTO = dentistaMapper.toDto(dentista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDentistaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dentistaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dentista in the database
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDentista() throws Exception {
        // Initialize the database
        dentistaRepository.saveAndFlush(dentista);

        int databaseSizeBeforeDelete = dentistaRepository.findAll().size();

        // Delete the dentista
        restDentistaMockMvc
            .perform(delete(ENTITY_API_URL_ID, dentista.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dentista> dentistaList = dentistaRepository.findAll();
        assertThat(dentistaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
