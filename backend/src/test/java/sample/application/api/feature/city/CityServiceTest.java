package sample.application.api.feature.city;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {
    @Mock
    private CityRepository repository;

    @InjectMocks
    private CityService service;

    @Test
    void insert() {
        final var cidade = new City("Nova Cidade");
        final var cidadeSalva = new City(1, "Nova Cidade");

        Mockito.when(repository.saveAndFlush(cidade)).thenReturn(cidadeSalva);

        final var cidadeInserida = service.save(cidade);
        assertNotNull(cidadeInserida);
        assertEquals(cidadeSalva, cidadeInserida);
    }
}
