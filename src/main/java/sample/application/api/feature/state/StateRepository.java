package sample.application.api.feature.state;

import org.springframework.stereotype.Repository;
import sample.application.api.shared.EntityRepository;

import java.util.List;

@Repository
public interface StateRepository extends EntityRepository<State> {
    List<State> findByNameLike(String name);
}
