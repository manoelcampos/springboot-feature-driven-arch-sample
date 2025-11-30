package sample.application.api.feature.city;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.application.api.shared.controller.AbstractController;

@RestController
@RequestMapping("/city")
public class CityController extends AbstractController<City, CityDTO, CityRepository, CityService> {
    public CityController(final CityService service) {
        super(CityDTO.class, service);
    }
}
