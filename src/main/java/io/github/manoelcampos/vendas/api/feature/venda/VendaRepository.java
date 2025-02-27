package io.github.manoelcampos.vendas.api.feature.venda;

import io.github.manoelcampos.vendas.api.shared.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends EntityRepository<Venda> {
}
