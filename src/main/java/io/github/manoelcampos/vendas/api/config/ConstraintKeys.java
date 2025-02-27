package io.github.manoelcampos.vendas.api.config;

import io.github.manoelcampos.vendas.api.model.AbstractBaseModel;
import io.github.manoelcampos.vendas.api.shared.controller.AbstractController;
import io.github.manoelcampos.vendas.api.shared.controller.RestExceptionHandler;
import io.github.manoelcampos.vendas.api.shared.util.ConstraintViolation;
import jakarta.persistence.Entity;

/**
 * Define nomes de unique contraints (UCs) para {@link Entity}s no banco.
 * Quando uma dessas constraints é violada e uma exceção é gerada,
 * o {@link RestExceptionHandler} verifica
 * se o nome da constraint estava contido na mensagem de erro,
 * gerando uma mensagem de erro amigável para o usuário no front.
 *
 * <p>O nome de cada UC deve seguir o padrão UC_NOME_TABELA_ORIGEM__CAMPO1__CAMPO2__CAMPO_N___.
 * Os nomes usados podem ser como desejar e tais nomes são formatados
 * para serem exibidos na mensagem de erro pro front.
 * O __ separa a tabela e cada um dos campos no nome da constaint.
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

    public static final String UC_ESTADO_DESCRICAO = "uc_estado__descricao___";
    public static final String UC_CIDADE_DESCRICAO = "uc_cidade__descricao___";

    public static final String FK_CIDADE__ESTADO = "fk_cidade__estado";
    public static final String FK_CLIENTE_CIDADE = "fk_cliente__cidade";

    public static final String FK_ITEM_VENDA__VENDA = "fk_item_venda__venda";
    public static final String FK_ITEM_VENDA__PRODUTO = "fk_item_venda__produto";
}
