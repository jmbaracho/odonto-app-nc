package br.com.jmb.odontoappnc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jmb.odontoappnc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AtendimentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Atendimento.class);
        Atendimento atendimento1 = new Atendimento();
        atendimento1.setId(1L);
        Atendimento atendimento2 = new Atendimento();
        atendimento2.setId(atendimento1.getId());
        assertThat(atendimento1).isEqualTo(atendimento2);
        atendimento2.setId(2L);
        assertThat(atendimento1).isNotEqualTo(atendimento2);
        atendimento1.setId(null);
        assertThat(atendimento1).isNotEqualTo(atendimento2);
    }
}
