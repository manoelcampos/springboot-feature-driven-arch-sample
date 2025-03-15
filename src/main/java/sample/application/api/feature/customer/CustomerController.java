package sample.application.api.feature.customer;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.application.api.shared.controller.AbstractController;

@RestController
@RequestMapping("/cliente")
public class CustomerController extends AbstractController<Customer, CustomerRepository, CustomerService> {
    public CustomerController(final CustomerService service) {
        super(service);
    }

    /**
     * Localiza um cliente pelo CPF no formato ddddddddddd ou ddd.ddd.ddd-dd.
     * Se o CPF for inválido, o método retorna status 400 (Bad Request).
     * @param cpf CPF do cliente
     * @return o cliente, caso tenha sido localizado
     */
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Customer> findByCpf(@PathVariable @CPF final String cpf) {
        return service
                    .findByCpf(cpf)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> newNotFoundException("%s não encontrado para o CPF " + cpf));
    }
}
