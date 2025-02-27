package io.github.manoelcampos.vendas.api.feature.venda;

import io.github.manoelcampos.dtogen.DTO;
import io.github.manoelcampos.vendas.api.feature.cliente.Cliente;
import io.github.manoelcampos.vendas.api.model.AbstractBaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

/**
 * @author Manoel Campos
 */
@Entity @DTO
public class Venda extends AbstractBaseModel {
    @NotNull @ManyToOne
    @DTO.MapToId
    public Cliente cliente;

    @Column(nullable = false)
    public LocalDateTime dataHora = LocalDateTime.now();

    @OneToMany(mappedBy = "venda")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @DTO.MapToId
    public List<ItemVenda> itens = new ArrayList<>();

    public Venda() {}

    public Venda(long id) {
        super(id);
    }

    public Venda(final long id, final List<ItemVenda> itens) {
        this(id);
        this.itens = itens;
    }

    public Venda(final Cliente cliente) {
        this.cliente = cliente;
    }

    public Venda(final long id, final Cliente cliente, final LocalDateTime dataHora, final List<ItemVenda> itens) {
        super(id);
        this.cliente = cliente;
        this.dataHora = dataHora;
        this.itens = itens;
    }

    public void setItens(final List<ItemVenda> itens) {
        this.itens = requireNonNullElse(itens, new ArrayList<>());
        this.itens.forEach(item -> item.venda = this);
    }

    @Override
    public String toString() {
        final Long clienteId = requireNonNullElse(cliente, new Cliente()).id;
        return "Venda{id: %d cliente: %d}".formatted(id, clienteId);
    }
}
