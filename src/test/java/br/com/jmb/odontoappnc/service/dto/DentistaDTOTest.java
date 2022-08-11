package br.com.jmb.odontoappnc.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jmb.odontoappnc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DentistaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DentistaDTO.class);
        DentistaDTO dentistaDTO1 = new DentistaDTO();
        dentistaDTO1.setId(1L);
        DentistaDTO dentistaDTO2 = new DentistaDTO();
        assertThat(dentistaDTO1).isNotEqualTo(dentistaDTO2);
        dentistaDTO2.setId(dentistaDTO1.getId());
        assertThat(dentistaDTO1).isEqualTo(dentistaDTO2);
        dentistaDTO2.setId(2L);
        assertThat(dentistaDTO1).isNotEqualTo(dentistaDTO2);
        dentistaDTO1.setId(null);
        assertThat(dentistaDTO1).isNotEqualTo(dentistaDTO2);
    }
}
