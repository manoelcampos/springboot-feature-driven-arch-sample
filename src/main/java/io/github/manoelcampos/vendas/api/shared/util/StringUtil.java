package io.github.manoelcampos.vendas.api.shared.util;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.text.WordUtils;

import java.util.Objects;

/**
 * @author Manoel Campos
 */
public final class StringUtil {
    /** Construtor privado para evitar instanciação da classe. */
    private StringUtil(){/**/}

    /**
     * Obtém um nome mais legível para uma coluna de uma tabela, para ser exibido em mensagens para o usuário.
     * Tal nome pode ser usado para formatar mensagens de erro (referentes a uma coluna de uma tabela)
     * de forma mais adequadas para o usuário.
     *
     * @param columnName nome de uma coluna no banco
     * @return um nome mais legível para a coluna
     */
    public static String formatColumnName(String columnName){
        /* Se o erro é um campo que representa uma chave estrangeira, chave o _id pois assim,
           fica apenas o nome da tabela relacionada que representa o atributo na classe para a chave estrangeira. */
        columnName = columnName.replaceAll("_id", "").replaceAll("_", " ");
        return camelCaseToSpace(columnName);
    }

    /**
     * Converte um texto (como o nome de uma tabela ou campo do banco) em um formato mais legível para humano,
     * para ser exibida em mensagens para o usuário.
     * Tal nome pode ser usado para formatar mensagens de erro (referentes a tal tabela)
     * de forma mais adequada para o usuário.
     *
     * @param text um texto normalmente usando underscore para separar palavras, a ser convertido para um texto mais legível
     * @return o texto convertido
     */
    public static String readableText(final String text){
        return WordUtils.capitalizeFully(text.replaceAll("_", " "));
    }

    /**
     * Converte um texto no formato "variasPalavrasJuntas" em "varias palavras juntas".
     *
     * @param text texto a ser convertido
     * @return o texto no novo formato
     */
    public static String camelCaseToSpace(final String text) {
        return RegExUtils.replacePattern(text, "(?<!^)([A-Z])", " $1").toLowerCase();
    }

    /**
     * {@return a String passada contendo apenas caracteres numéricos}
     * @param value valor para remover caracteres não numéricos
     */
    public static String onlyNumbers(final String value) {
        return Objects.requireNonNullElse(value, "").replaceAll("\\D", "");
    }
}
