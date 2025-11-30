package sample.application.api.feature.customer;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.application.api.shared.controller.AbstractController;

@RestController
@RequestMapping("/customer")
public class CustomerController extends AbstractController<Customer, CustomerDTO, CustomerRepository, CustomerService> {
    public CustomerController(final CustomerService service) {
        super(CustomerDTO.class, service);
    }

    @GetMapping("/social-security/{number}")
    public ResponseEntity<Customer> findBySocialSecurityNumber(@PathVariable @CPF final String number) {
        return getService()
                    .findBySocialSecurityNumber(number)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> newNotFoundException("%s não encontrado para o social security " + number));
    }
}
