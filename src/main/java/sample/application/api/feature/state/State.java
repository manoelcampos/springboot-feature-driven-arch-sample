package sample.application.api.feature.state;

import io.github.manoelcampos.dtogen.DTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sample.application.api.config.ConstraintKeys;
import sample.application.api.shared.model.AbstractBaseModel;

import java.util.Objects;

/// See [AbstractBaseModel] for the reason why all atributes are public.
/// @author Manoel Campos
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(name = ConstraintKeys.UC_STATE_NAME, columnNames = "name"),
    @UniqueConstraint(name = ConstraintKeys.UC_STATE_ABBREVIATION, columnNames = "abbreviation")
})
@DTO
public class State extends AbstractBaseModel {
    @NotNull @NotBlank
    public String name;

    @NotNull @NotBlank
    public String abbreviation;

    public State() {}

    public State(final long id) {
        super(id);
    }

    public State(final String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        System.out.println("Getting state.abbreviation: " + this.abbreviation);
        return Objects.requireNonNullElse(abbreviation, "");
    }

    public void setAbbreviation(final String abbreviation) {
        this.abbreviation = Objects.requireNonNullElse(abbreviation, "").toUpperCase();
        System.out.println("Setting state.abbreviation: " + this.abbreviation);
    }

    public void setName(final String name) {
        this.name = Objects.requireNonNullElse(name, "");
        System.out.println("Setting state.name: " + this.name);
    }
}
