package io.github.manoelcampos.vendas.api.feature.cliente;

import io.github.manoelcampos.vendas.api.controller.AbstractControllerTest;
import io.github.manoelcampos.vendas.api.shared.util.PathUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ClienteControllerTest extends AbstractControllerTest {
    private static final String RELATIVE_URL = "/cliente";
    private static final String BY_CPF_URL = PathUtil.concat(RELATIVE_URL, "/cpf/{cpf}");

    @Test
    void findByCpfInvalido() {
        final String cpfInvalido = "123";
        client().get()
                .uri(BY_CPF_URL, cpfInvalido)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void findByCpfInexistente() {
        final String cpfInexistente = "37633082020";
        client().get()
                .uri(BY_CPF_URL, cpfInexistente)
                .exchange()
                .expectStatus().isNotFound();
    }

    @ParameterizedTest
    @CsvSource({"33184755053", "331.847.550-53"})
    void findByCpfInvalido(final String cpf) {
        client().get()
                .uri(BY_CPF_URL, cpf)
                .exchange()
                .expectStatus().isOk();
    }

}
