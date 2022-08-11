package br.com.jmb.odontoappnc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jmb.odontoappnc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AtendimentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtendimentoDTO.class);
        AtendimentoDTO atendimentoDTO1 = new AtendimentoDTO();
        atendimentoDTO1.setId(1L);
        AtendimentoDTO atendimentoDTO2 = new AtendimentoDTO();
        assertThat(atendimentoDTO1).isNotEqualTo(atendimentoDTO2);
        atendimentoDTO2.setId(atendimentoDTO1.getId());
        assertThat(atendimentoDTO1).isEqualTo(atendimentoDTO2);
        atendimentoDTO2.setId(2L);
        assertThat(atendimentoDTO1).isNotEqualTo(atendimentoDTO2);
        atendimentoDTO1.setId(null);
        assertThat(atendimentoDTO1).isNotEqualTo(atendimentoDTO2);
    }
}
