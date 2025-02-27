package io.github.manoelcampos.vendas.api.feature.produto;

import io.github.manoelcampos.vendas.api.shared.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService extends AbstractCrudService<Produto, ProdutoRepository> {
    public ProdutoService(final ProdutoRepository repository) {
        super(repository);
    }

    @Override
    public boolean deleteById(final long id) {
        this.repository.findById(id).ifPresent(prod -> {
            if(prod.hasEstoque())
                throw new IllegalStateException("Produto %s não pode ser excluído pois ainda tem estoque".formatted(prod.descricao));
        });

        return super.deleteById(id);
    }
}
