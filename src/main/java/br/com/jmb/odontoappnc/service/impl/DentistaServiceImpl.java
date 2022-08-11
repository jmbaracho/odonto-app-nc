package br.com.jmb.odontoappnc.service.impl;

import br.com.jmb.odontoappnc.domain.Dentista;
import br.com.jmb.odontoappnc.repository.DentistaRepository;
import br.com.jmb.odontoappnc.service.DentistaService;
import br.com.jmb.odontoappnc.service.dto.DentistaDTO;
import br.com.jmb.odontoappnc.service.mapper.DentistaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Dentista}.
 */
@Service
@Transactional
public class DentistaServiceImpl implements DentistaService {

    private final Logger log = LoggerFactory.getLogger(DentistaServiceImpl.class);

    private final DentistaRepository dentistaRepository;

    private final DentistaMapper dentistaMapper;

    public DentistaServiceImpl(DentistaRepository dentistaRepository, DentistaMapper dentistaMapper) {
        this.dentistaRepository = dentistaRepository;
        this.dentistaMapper = dentistaMapper;
    }

    @Override
    public DentistaDTO save(DentistaDTO dentistaDTO) {
        log.debug("Request to save Dentista : {}", dentistaDTO);
        Dentista dentista = dentistaMapper.toEntity(dentistaDTO);
        dentista = dentistaRepository.save(dentista);
        return dentistaMapper.toDto(dentista);
    }

    @Override
    public DentistaDTO update(DentistaDTO dentistaDTO) {
        log.debug("Request to save Dentista : {}", dentistaDTO);
        Dentista dentista = dentistaMapper.toEntity(dentistaDTO);
        dentista = dentistaRepository.save(dentista);
        return dentistaMapper.toDto(dentista);
    }

    @Override
    public Optional<DentistaDTO> partialUpdate(DentistaDTO dentistaDTO) {
        log.debug("Request to partially update Dentista : {}", dentistaDTO);

        return dentistaRepository
            .findById(dentistaDTO.getId())
            .map(existingDentista -> {
                dentistaMapper.partialUpdate(existingDentista, dentistaDTO);

                return existingDentista;
            })
            .map(dentistaRepository::save)
            .map(dentistaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DentistaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dentistas");
        return dentistaRepository.findAll(pageable).map(dentistaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DentistaDTO> findOne(Long id) {
        log.debug("Request to get Dentista : {}", id);
        return dentistaRepository.findById(id).map(dentistaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dentista : {}", id);
        dentistaRepository.deleteById(id);
    }
}
