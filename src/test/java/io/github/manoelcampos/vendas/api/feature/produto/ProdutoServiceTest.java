package io.github.manoelcampos.vendas.api.feature.produto;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProdutoServiceTest extends AbstractServiceTest {
    /**
     * Cria um objeto service real e injeta nele os mocks criados com a anotação @Mock.
     */
    @InjectMocks
    private ProdutoService service;

    @Mock // cria um objeto fake (falso, uma marionete) para simular o comportamento de um objeto real
    private ProdutoRepository repository;

    @Test
    void deleteById() {
        final long id = 1L;
        final var prod = new Produto(id, "Produto 1", 10.0, 5);
        Mockito
                .when(repository.findById(id))
                .thenReturn(Optional.of(prod));

        assertThrows(IllegalStateException.class, () -> service.deleteById(id));
    }
}
