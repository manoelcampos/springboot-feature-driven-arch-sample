package sample.application.api.shared.util;

import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Adapter contendo funções utilitárias para manipulação de datas,
 * Armazena internamente uma data passada por parâmetro,
 * que pode ser nula. Assim, o método se encarrega de fazer as devidas
 * verificações evitando {@link NullPointerException}.
 * @author Manoel Campos
 */
public record DateAdapter(@Nullable LocalDate date) {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String format(final LocalDate fimVigenciaAdicional) {
        return DATE_FORMATTER.format(fimVigenciaAdicional);
    }

    /**
     * Verifica se a {@link #date() data} armazenada é igual ou posterior à data atual.
     */
    public boolean isEqualOrAfterNow() {
        return isEqualOrAfter(LocalDate.now());
    }

    /**
     * Verifica se a {@link #date() data} armazenada é igual ou posterior a uma outra data.
     */
    public boolean isEqualOrAfter(final LocalDate other) {
        final var dayBeforeThis = this.date == null ? LocalDate.MIN : this.date.minusDays(1);
        return dayBeforeThis.isAfter(other);
    }

    /**
     * Verifica se a {@link #date() data} armazenada é posterior de uma outra data.
     */
    public boolean isAfter(final LocalDate other) {
        final var thisDate = this.date == null ? LocalDate.MIN : this.date;
        return thisDate.isAfter(other);
    }

    /**
     * Verifica se a {@link #date() data} armazenada é igual ou anterior à data atual.
     */
    public boolean isEqualOrBeforeNow() {
        return isEqualOrBefore(LocalDate.now());
    }

    /**
     * Verifica se a {@link #date() data} armazenada é igual ou anterior a uma outra data.
     */
    public boolean isEqualOrBefore(final LocalDate other) {
        final var dayAfterThis = this.date == null ? LocalDate.MAX : this.date.plusDays(1);
        return dayAfterThis.isBefore(other);
    }

    /**
     * Verifica se a {@link #date() data} armazenada é anterior a uma outra data.
     * Se a data armazenada for válida mas não for informada uma data nula como parâmetro,
     * indica que não há limite de data para comparar.
     * Assim, a data armazenada será considerada menor que a data informada.
     * Tal situação é útil, por exemplo, quando deseja-se verificar se a data armazenada
     * não chegou a uma data limite de vigência.
     * Se não existir um limite de vigência (indicando que a data armazenada é válida por tempo indeterminado),
     * então a data armazenada deve ser considerada como anterior à data informada (ou seja, vigente).
     * @param other outra data para comparar
     */
    public boolean isBefore(@Nullable final LocalDate other) {
        final var thisDate = this.date == null ? LocalDate.MAX : this.date;
        final var otherDate = other == null ? LocalDate.MAX : other;
        return thisDate.isBefore(otherDate);
    }
}
