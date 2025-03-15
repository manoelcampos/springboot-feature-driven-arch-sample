package sample.application.api.feature.product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.application.api.shared.controller.AbstractController;

@RestController
@RequestMapping("/produto")
public class ProdutoController extends AbstractController<Product, ProdutoRepository, ProdutoService> {
    public ProdutoController(final ProdutoService service) {
        super(service);
    }
}
