package br.com.jmb.odontoappnc.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DentistaMapperTest {

    private DentistaMapper dentistaMapper;

    @BeforeEach
    public void setUp() {
        dentistaMapper = new DentistaMapperImpl();
    }
}
