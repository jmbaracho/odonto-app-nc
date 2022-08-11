package br.com.jmb.odontoappnc.service.mapper;

import br.com.jmb.odontoappnc.domain.Dentista;
import br.com.jmb.odontoappnc.service.dto.DentistaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dentista} and its DTO {@link DentistaDTO}.
 */
@Mapper(componentModel = "spring")
public interface DentistaMapper extends EntityMapper<DentistaDTO, Dentista> {}
