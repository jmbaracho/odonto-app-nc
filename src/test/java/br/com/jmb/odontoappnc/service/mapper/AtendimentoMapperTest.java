package br.com.jmb.odontoappnc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AtendimentoMapperTest {

    private AtendimentoMapper atendimentoMapper;

    @BeforeEach
    public void setUp() {
        atendimentoMapper = new AtendimentoMapperImpl();
    }
}
