package sample.application.api.feature.city;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import sample.application.api.feature.AbstractRepositoryTest;
import sample.application.api.feature.state.State;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private CityRepository repository;
    private City instance;

    @BeforeEach
    void setUp() {
        this.instance = new City("Cidade 1", new State(1));
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
        final List<City> result = repository.findByNameLike(descricao);
        assertEquals(1, result.size());
        assertEquals(descricao, result.getFirst().name);
    }

    @Test
    void inserirDescricaoDuplicadaGeraExcecao() {
        final var cidade = new City(instance.name, new State(1));
        assertThrows(DataIntegrityViolationException.class, () -> repository.save(cidade));
    }
}
