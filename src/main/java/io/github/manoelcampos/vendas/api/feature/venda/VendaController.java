package io.github.manoelcampos.vendas.api.feature.venda;

import io.github.manoelcampos.vendas.api.shared.controller.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/venda")
public class VendaController extends AbstractController<Venda, VendaRepository, VendaService> {
    public VendaController(final VendaService service) {
        super(service);
    }
}
