package br.com.jmb.odontoappnc.service.mapper;

import br.com.jmb.odontoappnc.domain.Atendimento;
import br.com.jmb.odontoappnc.domain.Dentista;
import br.com.jmb.odontoappnc.domain.Paciente;
import br.com.jmb.odontoappnc.domain.Procedimento;
import br.com.jmb.odontoappnc.service.dto.AtendimentoDTO;
import br.com.jmb.odontoappnc.service.dto.DentistaDTO;
import br.com.jmb.odontoappnc.service.dto.PacienteDTO;
import br.com.jmb.odontoappnc.service.dto.ProcedimentoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Atendimento} and its DTO {@link AtendimentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AtendimentoMapper extends EntityMapper<AtendimentoDTO, Atendimento> {
    @Mapping(target = "procedimentos", source = "procedimentos", qualifiedByName = "procedimentoDescricaoSet")
    @Mapping(target = "dentista", source = "dentista", qualifiedByName = "dentistaNomeDentista")
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "pacienteNomePaciente")
    AtendimentoDTO toDto(Atendimento s);

    @Mapping(target = "removeProcedimento", ignore = true)
    Atendimento toEntity(AtendimentoDTO atendimentoDTO);

    @Named("procedimentoDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    ProcedimentoDTO toDtoProcedimentoDescricao(Procedimento procedimento);

    @Named("procedimentoDescricaoSet")
    default Set<ProcedimentoDTO> toDtoProcedimentoDescricaoSet(Set<Procedimento> procedimento) {
        return procedimento.stream().map(this::toDtoProcedimentoDescricao).collect(Collectors.toSet());
    }

    @Named("dentistaNomeDentista")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomeDentista", source = "nomeDentista")
    DentistaDTO toDtoDentistaNomeDentista(Dentista dentista);

    @Named("pacienteNomePaciente")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomePaciente", source = "nomePaciente")
    PacienteDTO toDtoPacienteNomePaciente(Paciente paciente);
}
