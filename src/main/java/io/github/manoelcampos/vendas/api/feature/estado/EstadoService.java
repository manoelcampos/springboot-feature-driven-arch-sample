package io.github.manoelcampos.vendas.api.feature.estado;

import io.github.manoelcampos.vendas.api.shared.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class EstadoService extends AbstractCrudService<Estado, EstadoRepository> {
    public EstadoService(final EstadoRepository repository) {
        super(repository);
    }
}
