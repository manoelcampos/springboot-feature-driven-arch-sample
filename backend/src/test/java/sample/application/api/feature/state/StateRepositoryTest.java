package sample.application.api.feature.state;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A anotação @DataJpaTest é usada para testes de repositórios.
 * Ela vai criar uma conexão com o banco de dados
 * e permitir testar os repositórios.
 */
@DataJpaTest
class StateRepositoryTest {
    public static final String NOME_ESPERADO = "Novo Estado";
    /* Anotação @Autowired para injetar a instância do repositório.
    * Ou seja, ela cria uma instância do objeto pra gente. */
    @Autowired
    private StateRepository repository;

    @Test
    void findById() {
        final long id = 1;
        final var nomeEsperado = "São Paulo";
        findById(id, nomeEsperado);
    }

    private void findById(long id, String nomeEsperado) {
        State state = repository.findById(id).orElseThrow();
        assertEquals(nomeEsperado, state.name);
    }

    @Test
    void insereComSucesso() {
        final long id = cadastrarEstado();
        System.out.println(id);
        assertNotEquals(0, id);
        findById(id, NOME_ESPERADO);
    }

    /**
     * Insere um estado no banco
     * @return o ID do estado inserido
     * @throws ConstraintViolationException caso a validação dos dados do estado falhe e o estado não seja inserido
     */
    private long cadastrarEstado() {
        final var estado = new State();
        estado.name = NOME_ESPERADO;
        estado.abbreviation = "NE";
        final var novoEstado = repository.save(estado);

        /* Usa requireNonNullElse para retornar zero caso o ID seja null.
        * Mas esta é apenas uma verificação para o IDE não reclamar em outros pontos
        * que o ID pode ser nulo.
        * O ID só seria nulo se o estado não for inserido, que vai dar erro na linha
        * de cima e nem chega aqui. */
        return Objects.requireNonNullElse(novoEstado.getId(), 0L);
    }

    @Test
    void erroAoInserirSemDescricao() {
        final var estado = new State();
        estado.abbreviation = "NE";
        assertThrows(ConstraintViolationException.class, () -> repository.save(estado));
    }


    @Test
    void deleteByIdExcluiCadastro() {
        final long id = cadastrarEstado();
        repository.deleteById(id);

        // Envia o comando SQL imediatamente para o banco
        repository.flush();

        assertTrue(repository.findById(id).isEmpty());
    }

    @Test
    void deleteByIdViolaChaveEstrangeira() {
        final long id = 1; // São Paulo

        var exception = assertThrows(DataIntegrityViolationException.class, () -> {
            repository.deleteById(id);
            // Envia o comando SQL imediatamente para o banco
            repository.flush();
        });

        final var constraintName = "FK_CITY__STATE";
        assertTrue(exception.getMessage().contains(constraintName));
    }

}
