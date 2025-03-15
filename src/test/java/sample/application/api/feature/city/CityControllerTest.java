package sample.application.api.feature.city;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.Preconditions;
import sample.application.api.controller.AbstractControllerTest;
import sample.application.api.feature.state.State;
import sample.application.api.shared.util.PathUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Testes para a API REST de {@link City} implementada pelo {@link CityController}.
 * @author Manoel Campos
 */
class CityControllerTest extends AbstractControllerTest {
    private static final String RELATIVE_URL = "/cidade";
    private static final String BY_ID_URL = PathUtil.concat(RELATIVE_URL, "/{id}");

    @Test
    void findById() {
        final long id = 1;
        final var distrito = new City(id, "Cidade 1");
        client().get()
                .uri(BY_ID_URL, id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(City.class)
                .isEqualTo(distrito);
    }

    @Test
    void insertAndDelete() {
        final var cidade = new City("Nova Cidade", new State(1));
        final var novaCidade = insert(cidade);
        assertEquals(cidade.name, novaCidade.name);
        assertNotNull(novaCidade.getId());
        delete(novaCidade.getId());
    }

    /**
     * Insere um novo Cidade na base de dados e retorna o Cidade inserido.
     * @param cityParaInserir Cidade para ser inserido
     * @return novo Cidade com o id gerado
     */
    private City insert(final City cityParaInserir) {
        Preconditions.condition(cityParaInserir.getId() == null, "O id do Cidade a ser inserido deve ser nulo");

        return client().post()
                       .uri(RELATIVE_URL)
                       .bodyValue(cityParaInserir)
                       .exchange()
                       .expectStatus()
                       .isCreated()
                       .expectBody(City.class)
                       .returnResult()
                       .getResponseBody();
    }

    private void delete(final long id) {
        client().delete()
                .uri(BY_ID_URL, id)
                .exchange()
                .expectStatus()
                .isNoContent();

        client().get()
                .uri(BY_ID_URL, id)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
