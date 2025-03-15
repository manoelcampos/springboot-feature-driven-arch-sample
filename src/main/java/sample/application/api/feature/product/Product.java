package sample.application.api.feature.product;

import io.github.manoelcampos.dtogen.DTO;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sample.application.api.feature.purchase.PurchaseItem;
import sample.application.api.shared.model.AbstractBaseModel;

/**
 * See {@link AbstractBaseModel} for the reason why all atributes are public.
 * @author Manoel Campos
 */
@Entity @DTO
public class Product extends AbstractBaseModel {
    @NotNull @NotBlank
    public String description;

    @NotNull @DecimalMin("0.1")
    public double price;

    @NotNull @Min(0)
    public int amount;

    public Product() {}

    public Product(final long id) {
        super(id);
    }

    public Product(final long id, final String description, final double price, final int amount) {
        super(id);
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public boolean hasInventory(){
        return amount > 0;
    }

    public boolean isInventoryEnough(final PurchaseItem item){
        return this.amount < item.quant;
    }
}
