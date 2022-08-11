package br.com.jmb.odontoappnc.service.mapper;

import br.com.jmb.odontoappnc.domain.Paciente;
import br.com.jmb.odontoappnc.service.dto.PacienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paciente} and its DTO {@link PacienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface PacienteMapper extends EntityMapper<PacienteDTO, Paciente> {}
