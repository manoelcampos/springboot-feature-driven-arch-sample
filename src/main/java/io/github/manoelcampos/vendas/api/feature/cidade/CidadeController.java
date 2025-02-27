package io.github.manoelcampos.vendas.api.feature.cidade;

import io.github.manoelcampos.vendas.api.shared.controller.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cidade")
public class CidadeController extends AbstractController<Cidade, CidadeRepository, CidadeService> {
    public CidadeController(final CidadeService service) {
        super(service);
    }
}
