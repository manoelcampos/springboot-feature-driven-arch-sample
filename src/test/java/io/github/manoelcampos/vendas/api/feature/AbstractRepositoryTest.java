package io.github.manoelcampos.vendas.api.feature;

import io.github.manoelcampos.vendas.api.shared.util.ConstraintViolation;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** Classe base para a implementação de testes de integração dos {@link org.springframework.stereotype.Repository}.
 * AVISO: As subclasses não devem ser final. Incluindo final é gerado um warning.
 * @author Manoel Campos
 */
@DataJpaTest
public abstract class AbstractRepositoryTest {
    /**
     * Verifica se uma exceção lançada para uma operação de banco de dados
     * violou uma constraint específica.
     * @param ex exceção gerada
     * @param constraintName nome da constrainta que espera-se que tenha sido violada (como uma UC ou FK)
     */
    protected static void assertConstraintViolation(final DataIntegrityViolationException ex, final String constraintName) {
        final var msg = "O nome da constraint esperada de ser violada não foi identificado: " + ex.getMessage();
        assertTrue(ConstraintViolation.regexMatch(ex, constraintName).isPresent(), msg);
    }
}
