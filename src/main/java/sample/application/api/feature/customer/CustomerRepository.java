package sample.application.api.feature.customer;

import org.springframework.stereotype.Repository;
import sample.application.api.shared.EntityRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends EntityRepository<Customer> {
    Optional<Customer> findBySocialSecurityNumber(String socialSecurityNumber);

    /**
     * {@return lista de clientes cujo name contenha um determinado valor parcial}
     * @param name name do cliente, que pode conter um % em qualquer parte, para fazer uma busca parcial, como:
     *             <ul>
     *               <li>"Manoel%" para buscar todos os clientes cujo name comece com "Manoel".</li>
     *               <li>"%Campos%" para buscar todos os clientes cujo name contenha "Campos" em qualquer parte.</li>
     *               <li>"%Silva" para buscar todos os clientes cujo name termine com "Silva".</li>
     *             </ul>
     */
    List<Customer> findByNameLike(String name);

    /**
     * {@return uma lista de clientes de uma determinada cidade}
     * @param cityId ID da cidade para localizar os clientes.
     */
    List<Customer> findByCityId(long cityId);
}
