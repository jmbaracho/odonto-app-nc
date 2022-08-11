package br.com.jmb.odontoappnc.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jmb.odontoappnc.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DentistaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dentista.class);
        Dentista dentista1 = new Dentista();
        dentista1.setId(1L);
        Dentista dentista2 = new Dentista();
        dentista2.setId(dentista1.getId());
        assertThat(dentista1).isEqualTo(dentista2);
        dentista2.setId(2L);
        assertThat(dentista1).isNotEqualTo(dentista2);
        dentista1.setId(null);
        assertThat(dentista1).isNotEqualTo(dentista2);
    }
}
