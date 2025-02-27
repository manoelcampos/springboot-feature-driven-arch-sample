package io.github.manoelcampos.vendas.api.feature.cidade;

import io.github.manoelcampos.vendas.api.shared.EntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends EntityRepository<Cidade> {
    List<Cidade> findByDescricaoLike(String descricao);
}
