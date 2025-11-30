package sample.application.api.feature.city;

import org.springframework.stereotype.Repository;
import sample.application.api.shared.EntityRepository;

import java.util.List;

@Repository
public interface CityRepository extends EntityRepository<City> {
    List<City> findByNameLike(String name);
}
