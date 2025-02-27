package io.github.manoelcampos.vendas.api.feature.venda;

import io.github.manoelcampos.vendas.api.feature.cliente.Cliente;
import io.github.manoelcampos.vendas.api.feature.produto.AbstractServiceTest;
import io.github.manoelcampos.vendas.api.feature.produto.Produto;
import io.github.manoelcampos.vendas.api.feature.produto.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VendaServiceTest extends AbstractServiceTest {
    /**
     * @InjectMocks instancia o serviço,
     * mas pegando os objetos fake anotados com
     * @Mock e armazenando eles nos atributos
     * de mesmo tipo dentro do serviço.
     * Assim, o serviço não vai usar um objeto
     * real para tais atributos, mas o
     * fantoche (mock) que criamos.
     */
    @InjectMocks
    private VendaService service;

    /**
     * @Autowired é usada apenas pra instanciar objetos reais
     * @Mock cria um objeto fake, um fantoche que
     * você define como ele vai se comportar
     */
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private VendaRepository repository;

    private final Venda venda = new Venda(new Cliente(1));
    private final Produto prod1 = new Produto(1, "Prod 1", 100.0, 10);

    private void configurarMockProdutoRepository(Produto prod) {
        final Long id = Objects.requireNonNullElse(prod.getId(), 0L);
        // Mockito é a biblioteca de mock incluída no Spring
        Mockito
            .when(produtoRepository.findById(id))
            .thenReturn(Optional.of(prod));
    }

    @Test
    void insertProdutoSemEstoque() {
        configurarMockProdutoRepository(prod1);
        final var itens = List.of(new ItemVenda(1, 20), new ItemVenda(2, 5));
        venda.setItens(itens);

        assertThrows(IllegalStateException.class, () -> service.save(venda));
    }

    @Test
    void insertProdutoNaoInformado() {
        configurarMockProdutoRepository(prod1);
        final var itens = List.of(new ItemVenda(1, 2), new ItemVenda());
        venda.setItens(itens);

        assertThrows(IllegalStateException.class, () -> service.save(venda));
    }

    @Test
    void insertIdProdutoNaoInformado() {
        configurarMockProdutoRepository(prod1);
        final var itens = List.of(new ItemVenda(1, 2), new ItemVenda(new Produto()));
        venda.setItens(itens);
        assertThrows(IllegalStateException.class, () -> service.save(venda));
    }

    @Test
    void insertProdutoNaoLocalizado() {
        final var itens = List.of(new ItemVenda(3, 2));
        venda.setItens(itens);
        assertThrows(NoSuchElementException.class, () -> service.save(venda));
    }

    @Test
    void updateVendaNaoAlteraEstoqueProduto() {
        final var itens = List.of(new ItemVenda(1, 2), new ItemVenda());
        final var vendaExistente = new Venda(4, itens);
        Mockito.when(repository.saveAndFlush(vendaExistente)).thenReturn(vendaExistente);

        final var vendaObtida = service.save(vendaExistente);
        assertEquals(vendaExistente, vendaObtida);
    }

}
