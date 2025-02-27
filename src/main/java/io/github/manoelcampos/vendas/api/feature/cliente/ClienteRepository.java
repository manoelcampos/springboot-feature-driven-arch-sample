package io.github.manoelcampos.vendas.api.feature.cliente;

import io.github.manoelcampos.vendas.api.shared.EntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends EntityRepository<Cliente> {
    Optional<Cliente> findByCpf(String cpf);

    /**
     * {@return lista de clientes cujo nome contenha um determinado valor parcial}
     * @param nome nome do cliente, que pode conter um % em qualquer parte, para fazer uma busca parcial, como:
     *             <ul>
     *               <li>"Manoel%" para buscar todos os clientes cujo nome comece com "Manoel".</li>
     *               <li>"%Campos%" para buscar todos os clientes cujo nome contenha "Campos" em qualquer parte.</li>
     *               <li>"%Silva" para buscar todos os clientes cujo nome termine com "Silva".</li>
     *             </ul>
     */
    List<Cliente> findByNomeLike(String nome);

    /**
     * {@return uma lista de clientes de uma determinada cidade}
     * @param cidadeId ID da cidade para localizar os clientes.
     */
    List<Cliente> findByCidadeId(long cidadeId);
}
