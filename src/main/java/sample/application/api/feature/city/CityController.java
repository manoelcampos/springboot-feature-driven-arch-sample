package sample.application.api.feature.city;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.application.api.shared.controller.AbstractController;

@RestController
@RequestMapping("/cidade")
public class CityController extends AbstractController<City, CityRepository, CityService> {
    public CityController(final CityService service) {
        super(service);
    }
}
