package io.github.manoelcampos.vendas.api.feature.estado;

import io.github.manoelcampos.vendas.api.controller.AbstractControllerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EstadoControllerTest extends AbstractControllerTest {
    @Test
    void findById() {
        final var estadoEsperado = new Estado(1);
        estadoEsperado.descricao = "São Paulo";

        Estado estadoObtido = client.get()
                                      .uri("/estado/{id}", 1)
                                      .exchange() // envia a requisição
                                      .expectStatus()
                                      .isOk()
                                      .expectBody(Estado.class)
                                      .isEqualTo(estadoEsperado)
                                      .returnResult()
                                      .getResponseBody();

        assertNotNull(estadoObtido);
        assertEquals(estadoEsperado.descricao, estadoObtido.descricao);
    }
}
