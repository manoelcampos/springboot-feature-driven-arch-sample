package sample.application.api.config;

import jakarta.persistence.Entity;
import sample.application.api.shared.controller.AbstractController;
import sample.application.api.shared.controller.RestExceptionHandler;
import sample.application.api.shared.model.AbstractBaseModel;
import sample.application.api.shared.util.ConstraintViolation;

/**
 * Define nomes de unique constraints (UCs) para {@link Entity}s no banco.
 * Quando uma dessas constraints é violada e uma exceção é gerada,
 * o {@link RestExceptionHandler} verifica
 * se o nome da constraint estava contido na mensagem de erro,
 * gerando uma mensagem de erro amigável para o usuário no front.
 *
 * <p>O nome de cada UC deve seguir o padrão UC_NOME_TABELA_ORIGEM__CAMPO1__CAMPO2__CAMPO_N___.
 * Os nomes usados podem ser como desejar e tais nomes são formatados
 * para serem exibidos na mensagem de erro pro front.
 * O __ separa a tabela e cada um dos campos no nome da constraint.
 * E um ___ no final é usado para indicar onde o nome original da constraint acaba,
 * pois alguns bancos como o PostgreSQL adicional um sufixo para o nome destas constraints,
 * que não deve aparecer nas mensagems pro usuário.
 * Tal formato é definido em {@link ConstraintViolation#UC_FORMAT_REGEX}.
 * </p>
 *
 * <p>As constraints estão centralizadas aqui apenas para permitir documentar este formato para todas as UCs.</p>
 *
 * @see AbstractController#update(long, AbstractBaseModel)
 * @see AbstractController#insert(AbstractBaseModel)
 */
public final class ConstraintKeys {
    /** Construtor privado para evitar instânciar a classe. */
    private ConstraintKeys(){/**/}

    private static final String UC_SAMPLE = "uc_tabela__campo___";
    private static final String FK_SAMPLE = "fk_tabela_origem__tabela_destino";

    public static final String UC_STATE_NAME = "uc_state__name___";
    public static final String UC_STATE_ABBREVIATION = "uc_state__abbreviation___";
    public static final String UC_CITY_NAME = "uc_city__name___";

    public static final String FK_CITY__STATE = "fk_city__state";
    public static final String FK_CUSTOMER__CITY = "fk_customer__city";

    public static final String FK_ITEM__ORDER = "fk_item__order";
    public static final String FK_ORDER_ITEM__PRODUCT = "fk_order_item__product";
}
