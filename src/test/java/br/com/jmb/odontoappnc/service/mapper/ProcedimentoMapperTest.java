package br.com.jmb.odontoappnc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcedimentoMapperTest {

    private ProcedimentoMapper procedimentoMapper;

    @BeforeEach
    public void setUp() {
        procedimentoMapper = new ProcedimentoMapperImpl();
    }
}
