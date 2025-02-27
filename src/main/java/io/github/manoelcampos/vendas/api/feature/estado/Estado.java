package io.github.manoelcampos.vendas.api.feature.estado;

import io.github.manoelcampos.dtogen.DTO;
import io.github.manoelcampos.vendas.api.config.ConstraintKeys;
import io.github.manoelcampos.vendas.api.model.AbstractBaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = ConstraintKeys.UC_ESTADO_DESCRICAO, columnNames = "descricao"),
})
@DTO
public class Estado extends AbstractBaseModel {
    @NotNull @NotBlank
    public String descricao;

    @NotNull @NotBlank
    public String sigla;

    public Estado() {}

    public Estado(final long id) {
        super(id);
    }

    public Estado(final String descricao) {
        this.descricao =descricao;
    }
}
