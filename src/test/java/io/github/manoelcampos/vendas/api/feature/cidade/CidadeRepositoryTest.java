package io.github.manoelcampos.vendas.api.feature.cidade;

import io.github.manoelcampos.vendas.api.feature.AbstractRepositoryTest;
import io.github.manoelcampos.vendas.api.feature.estado.Estado;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CidadeRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private CidadeRepository repository;
    private Cidade instance;

    @BeforeEach
    void setUp() {
        this.instance = new Cidade("Cidade 1", new Estado(1));
        repository.save(instance);
    }

    @AfterEach
    void tearDown() {
        repository.delete(instance);
    }

    @Test
    void findById() {
        assertNotNull(instance.getId());
        assertTrue(repository.findById(instance.getId()).isPresent());
    }

    @Test
    void deleteById() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.deleteById(1L);
            repository.flush();
        });

        //assertConstraintViolation(ex, ConstraintKeys.FK_CIDADE__ESTADO);
    }

    @Test
    void findFirstByDescricao() {
        final var descricao = "Palmas";
        final List<Cidade> result = repository.findByDescricaoLike(descricao);
        assertEquals(1, result.size());
        assertEquals(descricao, result.getFirst().descricao);
    }

    @Test
    void inserirDescricaoDuplicadaGeraExcecao() {
        final var cidade = new Cidade(instance.descricao, new Estado(1));
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(cidade));
    }
}
