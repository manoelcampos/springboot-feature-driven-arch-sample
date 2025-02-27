package io.github.manoelcampos.vendas.api.feature.estado;

import io.github.manoelcampos.vendas.api.shared.EntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends EntityRepository<Estado> {
    List<Estado> findByDescricaoLike(String descricao);
}
