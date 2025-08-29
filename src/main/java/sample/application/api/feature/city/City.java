package sample.application.api.feature.city;

import io.github.manoelcampos.dtogen.DTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sample.application.api.config.ConstraintKeys;
import sample.application.api.feature.state.State;
import sample.application.api.shared.model.AbstractBaseModel;

import java.util.Objects;

/// See [AbstractBaseModel] for the reason why all atributes are public.
/// @author Manoel Campos
@Entity
@Table (uniqueConstraints = {
    @UniqueConstraint(name = ConstraintKeys.UC_CITY_NAME, columnNames = "name"),
})
@DTO
public class City extends AbstractBaseModel {
    @NotNull @NotBlank
    public String name;

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = ConstraintKeys.FK_CITY__STATE))
    @ManyToOne
    @DTO.MapToId
    public State state;

    public City() {}

    public City(final long id) {
        super(id);
    }

    public City(final String name) {
        this.name = name;
    }

    public City(final String name, final State state) {
        this.name = name;
        this.state = state;
    }

    public City(final long id, final String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        System.out.println("Getting cidade.descricao: " + name);
        return name;
    }

    public void setName(String descricao) {
        System.out.println("Setting cidade.descricao: " + descricao);

        if(Objects.requireNonNullElse(descricao, "").isBlank())
            throw new IllegalArgumentException("Descrição da cidade não pode ser vazia");
        this.name = descricao;
    }
}
