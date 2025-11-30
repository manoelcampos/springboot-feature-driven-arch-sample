package sample.application.api.feature.product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.application.api.shared.controller.AbstractController;

@RestController
@RequestMapping("/product")
public class ProdutoController extends AbstractController<Product, ProductDTO, ProdutoRepository, ProdutoService> {
    public ProdutoController(final ProdutoService service) {
        super(ProductDTO.class, service);
    }
}
