package io.github.manoelcampos.vendas.api.feature.cidade;

import io.github.manoelcampos.vendas.api.shared.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CidadeService extends AbstractCrudService<Cidade, CidadeRepository> {
    public CidadeService(final CidadeRepository repository) {
        super(repository);
    }
}
