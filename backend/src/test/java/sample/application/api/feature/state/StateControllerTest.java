package sample.application.api.feature.state;

import org.junit.jupiter.api.Test;
import sample.application.api.controller.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StateControllerTest extends AbstractControllerTest {
    @Test
    void findById() {
        final var estadoEsperado = new State(1);
        estadoEsperado.name = "São Paulo";

        State stateObtido = client().get()
                                  .uri("/state/{id}", 1)
                                  .exchange() // envia a requisição
                                  .expectStatus()
                                  .isOk()
                                  .expectBody(State.class)
                                  .isEqualTo(estadoEsperado)
                                  .returnResult()
                                  .getResponseBody();

        assertNotNull(stateObtido);
        assertEquals(estadoEsperado.name, stateObtido.name);
    }
}
