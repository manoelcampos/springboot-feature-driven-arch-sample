package io.github.manoelcampos.vendas.api.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/** Classe base para a implementação de testes de integração dos {@link org.springframework.stereotype.Repository}
 * AVISO: As subclasses não devem ser final. Incluindo final é gerado um warning.
 *
 * <p>
 * {@link AutoConfigureTestDatabase} é usado para NÃO substituir o banco criado pela aplicação,
 * pelo embedded db em memória que o {@link DataJpaTest} cria.
 * Assim, o mesmo banco da aplicação será usado para os testes (com as mesmas configurações).
 * </p>
 * @author Manoel Campos
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class AbstractRepositoryTest {

}
