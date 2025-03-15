package sample.application.api.feature.city;

import org.springframework.stereotype.Service;
import sample.application.api.shared.service.AbstractCrudService;

@Service
public class CityService extends AbstractCrudService<City, CityRepository> {
    public CityService(final CityRepository repository) {
        super(repository);
    }
}
