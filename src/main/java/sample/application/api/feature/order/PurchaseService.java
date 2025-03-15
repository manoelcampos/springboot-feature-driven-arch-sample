package sample.application.api.feature.order;

import org.springframework.stereotype.Service;
import sample.application.api.feature.product.Product;
import sample.application.api.feature.product.ProdutoRepository;
import sample.application.api.shared.service.AbstractCrudService;

import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNullElse;

@Service
public class PurchaseService extends AbstractCrudService<Purchase, PurchaseRepository> {
    private final ProdutoRepository produtoRepository;

    public PurchaseService(final PurchaseRepository repository, final ProdutoRepository produtoRepository) {
        super(repository);
        this.produtoRepository = produtoRepository;
    }

    /**
     * O método não leva em consideração que múltiplos clientes podem estar
     * comprando o mesmo produto ao mesmo tempo, e portanto,
     * a implementação pode acabar permitindo que o estoque fique negativo
     * nesses casos.
     */
    @Override
    public Purchase save(final Purchase purchase) {
        verificarEstoque(purchase);
        return super.save(purchase);
    }

    private void verificarEstoque(final Purchase purchase) {
        if(purchase.isEditing())
            return;

        for (PurchaseItem item : purchase.itens) {
            final var produto = item.product;

            final Long prodId = requireNonNullElse(produto, new Product()).id;
            if (prodId == null) {
                throw new IllegalStateException("Produto não informado");
            }

            final var prod =
                    produtoRepository
                            .findById(prodId)
                            .orElseThrow(() -> new NoSuchElementException("Produto não encontrado"));
            if(prod.isInventoryEnough(item)){
                throw new IllegalStateException("Produto %s não tem estoque suficiente".formatted(prod.description));
            }
        }
    }
}
