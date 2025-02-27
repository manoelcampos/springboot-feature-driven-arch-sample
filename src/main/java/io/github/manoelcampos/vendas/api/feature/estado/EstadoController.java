package io.github.manoelcampos.vendas.api.feature.estado;

import io.github.manoelcampos.vendas.api.shared.controller.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estado")
public class EstadoController extends AbstractController<Estado, EstadoRepository, EstadoService> {
    public EstadoController(final EstadoService service) {
        super(service);
    }
}
