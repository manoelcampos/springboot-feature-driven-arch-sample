package sample.application.api.feature.purchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.manoelcampos.dtogen.DTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import sample.application.api.config.ConstraintKeys;
import sample.application.api.feature.product.Product;
import sample.application.api.shared.model.AbstractBaseModel;

/// See [AbstractBaseModel] for the reason why all atributes are public.
/// @author Manoel Campos
@Entity @DTO
public class PurchaseItem extends AbstractBaseModel {
    @NotNull @ManyToOne @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = ConstraintKeys.FK_ITEM__ORDER))
    @DTO.MapToId
    public Purchase purchase;

    /**
     * Produto sendo vendido.
     * O valor do campo é ignorado em alterações (updates).
     * Se for feita uma alteração em algum item, o valor deste atributo é desconsiderado.
     * Depois da venda inserida, o produto não pode ser alterada.
     */
    @NotNull @ManyToOne
    @JoinColumn(updatable = false, foreignKey = @ForeignKey(name = ConstraintKeys.FK_ORDER_ITEM__PRODUCT))
    @DTO.MapToId
    public Product product;

    /**
     * Quantidade de itens do produto vendidos.
     * O valor do campo é ignorado em alterações (updates).
     * Se for feita uma alteração em algum item, o valor deste atributo é desconsiderado.
     * Depois da venda inserida, a quantidade não pode ser alterada.
     */
    @NotNull @Min(1) @Column(updatable = false)
    public int quant;

    public PurchaseItem() {}

    public PurchaseItem(final long id) {
        super(id);
    }

    public PurchaseItem(final Product product) {
        this.product = product;
    }

    public PurchaseItem(final long produtoId, final int quant) {
        this.product = new Product(produtoId);
        this.quant = quant;
    }
}
