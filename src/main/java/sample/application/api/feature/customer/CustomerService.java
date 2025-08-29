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

    /// Localiza um cliente pelo CPF nos formatos ddddddddddd ou ddd.ddd.ddd-dd.
    /// @param cpf CPF do cliente
    /// @return um [Optional] que pode conter o cliente localizado ou não.
    public Optional<Customer> findByCpf(final String cpf) {
        return repository.findBySocialSecurityNumber(StringUtil.onlyNumbers(cpf));
    }
}
