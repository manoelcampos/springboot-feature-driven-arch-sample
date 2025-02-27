package io.github.manoelcampos.vendas.api.feature.cliente;

import io.github.manoelcampos.dtogen.DTO;
import io.github.manoelcampos.vendas.api.config.ConstraintKeys;
import io.github.manoelcampos.vendas.api.feature.cidade.Cidade;
import io.github.manoelcampos.vendas.api.model.AbstractBaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

@Entity @DTO
public class Cliente extends AbstractBaseModel {
    @NotNull @NotBlank
    public String nome;

    @CPF @NotNull @NotBlank
    public String cpf;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = ConstraintKeys.FK_CLIENTE_CIDADE))
    @DTO.MapToId
    public Cidade cidade;

    public Cliente() {
    }

    public Cliente(final long id) {
        super(id);
    }
}
