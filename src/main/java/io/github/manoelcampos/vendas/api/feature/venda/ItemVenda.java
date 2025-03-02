package io.github.manoelcampos.vendas.api.feature.venda;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.manoelcampos.dtogen.DTO;
import io.github.manoelcampos.vendas.api.config.ConstraintKeys;
import io.github.manoelcampos.vendas.api.feature.produto.Produto;
import io.github.manoelcampos.vendas.api.model.AbstractBaseModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * See {@link AbstractBaseModel} for the reason why all atributes are public.
 * @author Manoel Campos
 */
@Entity @DTO
public class ItemVenda extends AbstractBaseModel {
    @NotNull @ManyToOne @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = ConstraintKeys.FK_ITEM_VENDA__VENDA))
    @DTO.MapToId
    public Venda venda;

    /**
     * Produto sendo vendido.
     * O valor do campo é ignorado em alterações (updates).
     * Se for feita uma alteração em algum item, o valor deste atributo é desconsiderado.
     * Depois da venda inserida, o produto não pode ser alterada.
     */
    @NotNull @ManyToOne
    @JoinColumn(updatable = false, foreignKey = @ForeignKey(name = ConstraintKeys.FK_ITEM_VENDA__PRODUTO))
    @DTO.MapToId
    public Produto produto;

    /**
     * Quantidade de itens do produto vendidos.
     * O valor do campo é ignorado em alterações (updates).
     * Se for feita uma alteração em algum item, o valor deste atributo é desconsiderado.
     * Depois da venda inserida, a quantidade não pode ser alterada.
     */
    @NotNull @Min(1) @Column(updatable = false)
    public int quant;

    public ItemVenda() {}

    public ItemVenda(final long id) {
        super(id);
    }

    public ItemVenda(final Produto produto) {
        this.produto = produto;
    }

    public ItemVenda(final long produtoId, final int quant) {
        this.produto = new Produto(produtoId);
        this.quant = quant;
    }
}
