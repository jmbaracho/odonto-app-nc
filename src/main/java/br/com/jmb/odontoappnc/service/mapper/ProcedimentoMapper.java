package br.com.jmb.odontoappnc.service.mapper;

import br.com.jmb.odontoappnc.domain.Procedimento;
import br.com.jmb.odontoappnc.service.dto.ProcedimentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Procedimento} and its DTO {@link ProcedimentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProcedimentoMapper extends EntityMapper<ProcedimentoDTO, Procedimento> {}
