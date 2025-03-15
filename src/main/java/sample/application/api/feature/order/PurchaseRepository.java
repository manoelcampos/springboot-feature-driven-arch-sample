package sample.application.api.feature.order;

import org.springframework.stereotype.Repository;
import sample.application.api.shared.EntityRepository;

@Repository
public interface PurchaseRepository extends EntityRepository<Purchase> {
}
