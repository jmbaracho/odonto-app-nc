package br.com.jmb.odontoappnc.service.impl;

import br.com.jmb.odontoappnc.domain.Procedimento;
import br.com.jmb.odontoappnc.repository.ProcedimentoRepository;
import br.com.jmb.odontoappnc.service.ProcedimentoService;
import br.com.jmb.odontoappnc.service.dto.ProcedimentoDTO;
import br.com.jmb.odontoappnc.service.mapper.ProcedimentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Procedimento}.
 */
@Service
@Transactional
public class ProcedimentoServiceImpl implements ProcedimentoService {

    private final Logger log = LoggerFactory.getLogger(ProcedimentoServiceImpl.class);

    private final ProcedimentoRepository procedimentoRepository;

    private final ProcedimentoMapper procedimentoMapper;

    public ProcedimentoServiceImpl(ProcedimentoRepository procedimentoRepository, ProcedimentoMapper procedimentoMapper) {
        this.procedimentoRepository = procedimentoRepository;
        this.procedimentoMapper = procedimentoMapper;
    }

    @Override
    public ProcedimentoDTO save(ProcedimentoDTO procedimentoDTO) {
        log.debug("Request to save Procedimento : {}", procedimentoDTO);
        Procedimento procedimento = procedimentoMapper.toEntity(procedimentoDTO);
        procedimento = procedimentoRepository.save(procedimento);
        return procedimentoMapper.toDto(procedimento);
    }

    @Override
    public ProcedimentoDTO update(ProcedimentoDTO procedimentoDTO) {
        log.debug("Request to save Procedimento : {}", procedimentoDTO);
        Procedimento procedimento = procedimentoMapper.toEntity(procedimentoDTO);
        procedimento = procedimentoRepository.save(procedimento);
        return procedimentoMapper.toDto(procedimento);
    }

    @Override
    public Optional<ProcedimentoDTO> partialUpdate(ProcedimentoDTO procedimentoDTO) {
        log.debug("Request to partially update Procedimento : {}", procedimentoDTO);

        return procedimentoRepository
            .findById(procedimentoDTO.getId())
            .map(existingProcedimento -> {
                procedimentoMapper.partialUpdate(existingProcedimento, procedimentoDTO);

                return existingProcedimento;
            })
            .map(procedimentoRepository::save)
            .map(procedimentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProcedimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Procedimentos");
        return procedimentoRepository.findAll(pageable).map(procedimentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProcedimentoDTO> findOne(Long id) {
        log.debug("Request to get Procedimento : {}", id);
        return procedimentoRepository.findById(id).map(procedimentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Procedimento : {}", id);
        procedimentoRepository.deleteById(id);
    }
}
