package sample.application.api.shared.model;

import jakarta.persistence.*;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/// All classes that have the [Entity] annotation must inherit from this class.
/// Those classes have all atributes define as public, since the
/// [auto-class-accessors-maven-plugin](https://github.com/manoelcampos/auto-class-accessors-maven-plugin) is being used.
/// This way, when there is a read/write to a field,
/// the respective getter/getter is called instead (if existing).
/// The plugin is just included inside the pom.xml and the m`agic happens when the project is built.
///
/// @author Manoel Campos
@MappedSuperclass
public abstract class AbstractBaseModel implements BaseModel {
    /**
     * Identificador único do objeto, gerado pelo banco de dados.
     * {@code @Column(nullalbe = false)} indica que o campo não pode ser nulo no banco.
     * {@code @Nullable} (usado no getter na interface) indica que o campo pode ser nulo na aplicação, pois ao inserir,
     * o registro é enviado sem um ID, que é gerado no banco.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    public Long id;

    public AbstractBaseModel() {
    }

    public AbstractBaseModel(final long id) {
        this.id = id;
    }

    /**
     * Verifica, de forma segura para Null Pointer Exception, se o ID da entidade é igual a um determinado valor.
     * @return true se o id da entidade for igual ao ID passado
     */
    public boolean isSameId(final long id){
        return this.id != null && this.id == id;
    }

    @Override public @Nullable Long getId() { return id; }

    @Override
    public String toString() {
        return "%s{id: %d}".formatted(getClass().getSimpleName(), id);
    }

    @Override
    public boolean equals(final Object that) {
        if(that.getClass() == this.getClass()){
            return Objects.equals(this.id, ((AbstractBaseModel) that).id);
        }

        return false;
    }
}
