package io.github.manoelcampos.vendas.api.feature.cliente;

import io.github.manoelcampos.vendas.api.shared.service.AbstractCrudService;
import io.github.manoelcampos.vendas.api.shared.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService extends AbstractCrudService<Cliente, ClienteRepository> {
    public ClienteService(final ClienteRepository repository) {
        super(repository);
    }

    /**
     * Localiza um cliente pelo CPF nos formatos ddddddddddd ou ddd.ddd.ddd-dd.
     * @param cpf CPF do cliente
     * @return um {@link Optional} que pode conter o cliente localizado ou não.
     */
    public Optional<Cliente> findByCpf(final String cpf) {
        return repository.findByCpf(StringUtil.onlyNumbers(cpf));
    }
}
