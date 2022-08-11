package br.com.jmb.odontoappnc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jmb.odontoappnc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcedimentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedimentoDTO.class);
        ProcedimentoDTO procedimentoDTO1 = new ProcedimentoDTO();
        procedimentoDTO1.setId(1L);
        ProcedimentoDTO procedimentoDTO2 = new ProcedimentoDTO();
        assertThat(procedimentoDTO1).isNotEqualTo(procedimentoDTO2);
        procedimentoDTO2.setId(procedimentoDTO1.getId());
        assertThat(procedimentoDTO1).isEqualTo(procedimentoDTO2);
        procedimentoDTO2.setId(2L);
        assertThat(procedimentoDTO1).isNotEqualTo(procedimentoDTO2);
        procedimentoDTO1.setId(null);
        assertThat(procedimentoDTO1).isNotEqualTo(procedimentoDTO2);
    }
}
