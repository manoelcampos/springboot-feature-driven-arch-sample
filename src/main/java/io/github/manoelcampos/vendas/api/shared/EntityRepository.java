package io.github.manoelcampos.vendas.api.shared;

import io.github.manoelcampos.vendas.api.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Fornece um contrato para criação de outras interfaces anotadas com
 * {@link org.springframework.stereotype.Repository}
 * e que manipulam entidades do tipo {@link BaseModel}.
 * A implementação de tais interfaces é criada automaticamente pelo Spring Data JPA
 * e instanciadas por meio de injeção de dependência.
 * @author Manoel Campos
 */
@NoRepositoryBean
public interface EntityRepository<T extends BaseModel> extends JpaRepository<T, Long> {
}
