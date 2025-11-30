package sample.application.api.feature.purchase;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.application.api.shared.controller.AbstractController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController extends AbstractController<Purchase, PurchaseDTO, PurchaseRepository, PurchaseService> {
    public PurchaseController(final PurchaseService service) {
        super(PurchaseDTO.class, service);
    }
}
