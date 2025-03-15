package sample.application.api.feature.purchase;

import io.github.manoelcampos.dtogen.DTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import sample.application.api.feature.customer.Customer;
import sample.application.api.shared.model.AbstractBaseModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

/**
 * See {@link AbstractBaseModel} for the reason why all atributes are public.
 * We cannot call the class Order, since it is a reserved word in SQL,
 * otherwise, we need to explicitly change the table name.
 * @author Manoel Campos
 */
@Entity @DTO
public class Purchase extends AbstractBaseModel {
    @NotNull @ManyToOne
    @DTO.MapToId
    public Customer customer;

    /** The field cannot be named "timestamp" since this is a reserved work for many databases. */
    @Column(nullable = false)
    public LocalDateTime dateTime = LocalDateTime.now();

    @OneToMany(mappedBy = "purchase")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @DTO.MapToId
    public List<PurchaseItem> itens = new ArrayList<>();

    public Purchase() {}

    public Purchase(long id) {
        super(id);
    }

    public Purchase(final long id, final List<PurchaseItem> itens) {
        this(id);
        this.itens = itens;
    }

    public Purchase(final Customer customer) {
        this.customer = customer;
    }

    public Purchase(final long id, final Customer customer, final LocalDateTime dataHora, final List<PurchaseItem> itens) {
        super(id);
        this.customer = customer;
        this.dateTime = dataHora;
        this.itens = itens;
    }

    public void setItens(final List<PurchaseItem> itens) {
        this.itens = requireNonNullElse(itens, new ArrayList<>());
        this.itens.forEach(item -> item.purchase = this);
    }

    @Override
    public String toString() {
        final Long customerId = requireNonNullElse(customer, new Customer()).id;
        return "Purchase{id: %d customer: %d}".formatted(id, customerId);
    }
}
