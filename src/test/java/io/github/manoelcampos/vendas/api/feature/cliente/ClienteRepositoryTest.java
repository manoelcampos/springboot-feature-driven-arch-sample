package io.github.manoelcampos.vendas.api.feature.cliente;

import io.github.manoelcampos.vendas.api.feature.AbstractRepositoryTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private ClienteRepository repository;

    @Test
    void findByCidadeId() {
        final var listaCliente = repository.findByCidadeId(1);
        assertEquals(2, listaCliente.size());
    }

    @ParameterizedTest
    @CsvSource({"Ana%,1", "%Silva,2", "%Pedro%,3"})
    void findByNomeLike(final String nome, final int count) {
        final var listaCliente = repository.findByNomeLike(nome);
        assertEquals(count, listaCliente.size());
    }
}
