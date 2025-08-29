package sample.application.api.shared;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import sample.application.api.shared.model.BaseModel;

/// Fornece um contrato para criação de outras interfaces anotadas com
/// [org.springframework.stereotype.Repository]
/// e que manipulam entidades do tipo [BaseModel].
/// A implementação de tais interfaces é criada automaticamente pelo Spring Data JPA
/// e instanciadas por meio de injeção de dependência.
/// @author Manoel Campos
@NoRepositoryBean
public interface EntityRepository<T extends BaseModel> extends JpaRepository<T, Long> {
}
