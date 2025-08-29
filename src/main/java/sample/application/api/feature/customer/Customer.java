package sample.application.api.feature.customer;

import io.github.manoelcampos.dtogen.DTO;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sample.application.api.config.ConstraintKeys;
import sample.application.api.feature.city.City;
import sample.application.api.shared.model.AbstractBaseModel;

/// See [AbstractBaseModel] for the reason why all atributes are public.
/// @author Manoel Campos
@Entity @DTO
public class Customer extends AbstractBaseModel {
    @NotNull @NotBlank
    public String name;

    @NotNull @NotBlank
    public String socialSecurityNumber;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = ConstraintKeys.FK_CUSTOMER__CITY))
    @DTO.MapToId
    public City city;

    public Customer() {
    }

    public Customer(final long id) {
        super(id);
    }
}
