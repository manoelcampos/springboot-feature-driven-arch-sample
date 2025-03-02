package io.github.manoelcampos.vendas.api.feature.estado;

import io.github.manoelcampos.vendas.api.shared.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class EstadoService extends AbstractCrudService<Estado, EstadoRepository> {
    public EstadoService(final EstadoRepository repository) {
        super(repository);
    }

    @Override
    public Estado save(final Estado estado) {
        if(estado.descricao.equalsIgnoreCase(estado.sigla))
            throw new IllegalStateException("A descrição do estado não pode ser igual à sigla");

        return super.save(estado);
    }
}
