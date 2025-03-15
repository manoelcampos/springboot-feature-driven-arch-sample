package sample.application.api.feature.purchase;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.application.api.shared.controller.AbstractController;

@RestController
@RequestMapping("/venda")
public class PurchaseController extends AbstractController<Purchase, PurchaseRepository, PurchaseService> {
    public PurchaseController(final PurchaseService service) {
        super(service);
    }
}
