package io.github.manoelcampos.vendas.api.feature.cidade;

import io.github.manoelcampos.vendas.api.controller.AbstractControllerTest;
import io.github.manoelcampos.vendas.api.feature.estado.Estado;
import io.github.manoelcampos.vendas.api.shared.util.PathUtil;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.Preconditions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Testes para a API REST de {@link Cidade} implementada pelo {@link CidadeController}.
 * @author Manoel Campos
 */
class CidadeControllerTest extends AbstractControllerTest {
    private static final String RELATIVE_URL = "/cidade";
    private static final String BY_ID_URL = PathUtil.concat(RELATIVE_URL, "/{id}");

    @Test
    void findById() {
        final long id = 1;
        final var distrito = new Cidade(id, "Cidade 1");
        client().get()
                .uri(BY_ID_URL, id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cidade.class)
                .isEqualTo(distrito);
    }

    @Test
    void insertAndDelete() {
        final var cidade = new Cidade("Nova Cidade", new Estado(1));
        final var novaCidade = insert(cidade);
        assertEquals(cidade.descricao, novaCidade.descricao);
        assertNotNull(novaCidade.getId());
        delete(novaCidade.getId());
    }

    /**
     * Insere um novo Cidade na base de dados e retorna o Cidade inserido.
     * @param cidadeParaInserir Cidade para ser inserido
     * @return novo Cidade com o id gerado
     */
    private Cidade insert(final Cidade cidadeParaInserir) {
        Preconditions.condition(cidadeParaInserir.getId() == null, "O id do Cidade a ser inserido deve ser nulo");

        return client().post()
                       .uri(RELATIVE_URL)
                       .bodyValue(cidadeParaInserir)
                       .exchange()
                       .expectStatus()
                       .isCreated()
                       .expectBody(Cidade.class)
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
