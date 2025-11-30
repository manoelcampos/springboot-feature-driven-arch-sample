package sample.application.api.feature.customer;

import org.springframework.stereotype.Service;
import sample.application.api.shared.service.AbstractCrudService;
import sample.application.api.shared.util.StringUtil;

import java.util.Optional;

@Service
public class CustomerService extends AbstractCrudService<Customer, CustomerRepository> {
    public CustomerService(final CustomerRepository repository) {
        super(repository);
    }

    public Optional<Customer> findBySocialSecurityNumber(final String socialSecurityNumber) {
        return getRepository().findBySocialSecurityNumber(StringUtil.onlyNumbers(socialSecurityNumber));
    }
}
