package sample.application.api.feature.product;

import org.springframework.stereotype.Service;
import sample.application.api.shared.service.AbstractCrudService;

@Service
public class ProdutoService extends AbstractCrudService<Product, ProdutoRepository> {
    public ProdutoService(final ProdutoRepository repository) {
        super(repository);
    }

    @Override
    public boolean deleteById(final long id) {
        this.repository.findById(id).ifPresent(prod -> {
            if(prod.hasInventory())
                throw new IllegalStateException("Produto %s não pode ser excluído pois ainda tem estoque".formatted(prod.description));
        });

        return super.deleteById(id);
    }
}
