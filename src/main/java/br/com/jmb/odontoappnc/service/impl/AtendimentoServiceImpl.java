package br.com.jmb.odontoappnc.service.impl;

import br.com.jmb.odontoappnc.domain.Atendimento;
import br.com.jmb.odontoappnc.repository.AtendimentoRepository;
import br.com.jmb.odontoappnc.service.AtendimentoService;
import br.com.jmb.odontoappnc.service.dto.AtendimentoDTO;
import br.com.jmb.odontoappnc.service.mapper.AtendimentoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Atendimento}.
 */
@Service
@Transactional
public class AtendimentoServiceImpl implements AtendimentoService {

    private final Logger log = LoggerFactory.getLogger(AtendimentoServiceImpl.class);

    private final AtendimentoRepository atendimentoRepository;

    private final AtendimentoMapper atendimentoMapper;

    public AtendimentoServiceImpl(AtendimentoRepository atendimentoRepository, AtendimentoMapper atendimentoMapper) {
        this.atendimentoRepository = atendimentoRepository;
        this.atendimentoMapper = atendimentoMapper;
    }

    @Override
    public AtendimentoDTO save(AtendimentoDTO atendimentoDTO) {
        log.debug("Request to save Atendimento : {}", atendimentoDTO);
        Atendimento atendimento = atendimentoMapper.toEntity(atendimentoDTO);
        atendimento = atendimentoRepository.save(atendimento);
        return atendimentoMapper.toDto(atendimento);
    }

    @Override
    public AtendimentoDTO update(AtendimentoDTO atendimentoDTO) {
        log.debug("Request to save Atendimento : {}", atendimentoDTO);
        Atendimento atendimento = atendimentoMapper.toEntity(atendimentoDTO);
        atendimento = atendimentoRepository.save(atendimento);
        return atendimentoMapper.toDto(atendimento);
    }

    @Override
    public Optional<AtendimentoDTO> partialUpdate(AtendimentoDTO atendimentoDTO) {
        log.debug("Request to partially update Atendimento : {}", atendimentoDTO);

        return atendimentoRepository
            .findById(atendimentoDTO.getId())
            .map(existingAtendimento -> {
                atendimentoMapper.partialUpdate(existingAtendimento, atendimentoDTO);

                return existingAtendimento;
            })
            .map(atendimentoRepository::save)
            .map(atendimentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AtendimentoDTO> findAll() {
        log.debug("Request to get all Atendimentos");
        return atendimentoRepository.findAll().stream().map(atendimentoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<AtendimentoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return atendimentoRepository.findAllWithEagerRelationships(pageable).map(atendimentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AtendimentoDTO> findOne(Long id) {
        log.debug("Request to get Atendimento : {}", id);
        return atendimentoRepository.findOneWithEagerRelationships(id).map(atendimentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Atendimento : {}", id);
        atendimentoRepository.deleteById(id);
    }
}
