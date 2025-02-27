package io.github.manoelcampos.vendas.api.feature.produto;

import io.github.manoelcampos.dtogen.DTO;
import io.github.manoelcampos.vendas.api.feature.venda.ItemVenda;
import io.github.manoelcampos.vendas.api.model.AbstractBaseModel;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Manoel Campos
 */
@Entity @DTO
public class Produto extends AbstractBaseModel {
    @NotNull @NotBlank
    public String descricao;

    @NotNull @DecimalMin("0.1")
    public double preco;

    @NotNull @Min(0)
    public int estoque;

    public Produto() {}

    public Produto(final long id) {
        super(id);
    }

    public Produto(final long id, final String descricao, final double preco, final int estoque) {
        super(id);
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
    }

    public boolean hasEstoque(){
        return estoque > 0;
    }

    public boolean isEstoqueInsuficiente(final ItemVenda item){
        return this.estoque < item.quant;
    }
}
