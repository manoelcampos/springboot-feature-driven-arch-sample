package io.github.manoelcampos.vendas.api.feature.cidade;

import io.github.manoelcampos.dtogen.DTO;
import io.github.manoelcampos.vendas.api.config.ConstraintKeys;
import io.github.manoelcampos.vendas.api.feature.estado.Estado;
import io.github.manoelcampos.vendas.api.model.AbstractBaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * See {@link AbstractBaseModel} for the reason why all atributes are public.
 * @author Manoel Campos
 */
@Entity
@Table (uniqueConstraints = {
    @UniqueConstraint(name = ConstraintKeys.UC_CIDADE_DESCRICAO, columnNames = "descricao"),
})
@DTO
public class Cidade extends AbstractBaseModel {
    @NotNull @NotBlank
    public String descricao;

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = ConstraintKeys.FK_CIDADE__ESTADO))
    @ManyToOne
    @DTO.MapToId
    public Estado estado;

    public Cidade() {}

    public Cidade(final long id) {
        super(id);
    }

    public Cidade(final String descricao) {
        this.descricao = descricao;
    }

    public Cidade(final String descricao, final Estado estado) {
        this.descricao = descricao;
        this.estado = estado;
    }

    public Cidade(final long id, final String descricao) {
        super(id);
        this.descricao = descricao;
    }

    public String getDescricao() {
        System.out.println("Getting cidade.descricao: " + descricao);
        return descricao;
    }

    public void setDescricao(String descricao) {
        System.out.println("Setting cidade.descricao: " + descricao);

        if(Objects.requireNonNullElse(descricao, "").isBlank())
            throw new IllegalArgumentException("Descrição da cidade não pode ser vazia");
        this.descricao = descricao;
    }
}
