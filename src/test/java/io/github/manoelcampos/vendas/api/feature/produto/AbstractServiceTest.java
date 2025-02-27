package io.github.manoelcampos.vendas.api.feature.produto;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Para rodar testes que usam mocks do Mockito,
 * é preciso usar a anotação @ExtendWith
 * para que os mocks sejam criados corretamente.
 * @author Manoel Campos
 */
@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT) // usado para ignorar quando um método do mock não é chamado como esperado
public abstract class AbstractServiceTest {
}
