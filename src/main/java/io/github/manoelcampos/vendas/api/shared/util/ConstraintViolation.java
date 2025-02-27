package io.github.manoelcampos.vendas.api.shared.util;

import io.github.manoelcampos.vendas.api.config.ConstraintKeys;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static io.github.manoelcampos.vendas.api.shared.util.StringUtil.readableText;

/**
 * Detecta a violação de uma constraint (como Foreign Key ou Unique Constraint) e
 * retorna informações legíveis para o usuário,
 * que podem ser exibidas em uma mensagem de erro amigável.
 *
 * @author Manoel Campos
 */
public final class ConstraintViolation {
    /** Construtor privado para evitar instânciar a classe. */
    private ConstraintViolation(){/**/}

    /**
     * Formato para nomes de Foreign Keys (FKs) no banco de dados, sendo fk_tabela_origem__tabela_destino,
     * onde tabela_origem é a tabela que possui a chave estrangeira e tabela_destino é a tabela referenciada.
     */
    //language=RegExp
    public static final String FK_FORMAT_REGEX = "fk_(\\w+)__(\\w+)";

    /**
     * Format para nomes de Unique Constraints (UCs) no banco de dados.
     * Mais detalhes em {@link ConstraintKeys}.
     */
    //language=RegExp
    public static final String UC_FORMAT_REGEX = "uc_(\\w+)(__(\\w+))+___";

    /**
     * Procura em uma mensagem de erro de uma exceção pelo nome de uma Foreign Key,
     * que indica que a FK foi violada.
     *
     * @param ex {@link DataIntegrityViolationException} lançada
     * @return um {@link Optional} contendo uma mensagem amigável sobre a violação de uma FK;
     * ou um Optiona vazio se a exceção foi devido a outro problema ou o nome de uma FK não foi encontrado.
     * @see #FK_FORMAT_REGEX
     */
    public static Optional<String> findForeignKeyMessage(final DataIntegrityViolationException ex) {
        final var matcherOptional = regexMatch(ex, FK_FORMAT_REGEX);
        return matcherOptional.map(ConstraintViolation::fkViolationMsg);
    }

    public static Optional<String> findUniqueConstraintMessage(final DataIntegrityViolationException ex) {
        final var matcherOptional = regexMatch(ex, UC_FORMAT_REGEX);
        return matcherOptional.map(ConstraintViolation::ucViolationMsg);
    }

    public static @NotNull Optional<MatchResult> regexMatch(final DataIntegrityViolationException ex, final String regex) {
        return getPattern(regex)
                      .matcher(ex.getMessage().toLowerCase())
                      .results()
                      .findFirst();
    }

    public static @NotNull Pattern getPattern(final String regex) {
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    /**
     * Retorna uma mensagem amigável para o usuário indicando que a violação de uma FK foi detectada.
     *
     * @param matcher objeto {@link MatchResult} retornado após a execução de uma regex para verificar
     *                se a mensagem de erro contém o nome de uma FK seguindo o {@link #FK_FORMAT_REGEX}
     * @return uma mensagem amigável para o usuário
     */
    private static String fkViolationMsg(final MatchResult matcher) {
        final var sourceTable = readableText(matcher.group(1));
        final var targetTable = readableText(matcher.group(2));
        return "Não foi possível excluir o(a) %s pois existe um(a) %s associado(a) a ele(a)".formatted(targetTable, sourceTable);
    }

    private static String ucViolationMsg(final MatchResult matcher) {
        final var groups = matcher.groupCount();
        final var sourceTable = readableText(matcher.group(1));
        final var fields = IntStream.range(2, groups)
                                    .mapToObj(matcher::group)
                                    .map(StringUtil::readableText)
                                    .toArray(String[]::new);


        final var msg = fields.length == 1 ? "o mesmo valor" : "os mesmos valores";
        return "Já existe um(a) %s com %s de %s".formatted(sourceTable, msg, String.join(", ", fields));
    }

}
