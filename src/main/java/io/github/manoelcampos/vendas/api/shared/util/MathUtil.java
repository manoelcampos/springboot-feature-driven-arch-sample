package io.github.manoelcampos.vendas.api.shared.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Manoel Campos
 */
public final class MathUtil {
    /**
     * Construtor privado para evitar a instanciação da classe.
     */
    private MathUtil() {/**/}

    /**
     * {@return a média a partir de um conjunto de valores já somados}
     * @param sum a soma dos valores para calcular a média
     * @param total total de itens para calcular a média
     */
    public static double average(final double sum, final int total) {
        return total == 0 ? 0 : sum/total;
    }

    /**
     * Formata um valor double representando um percentual com 2 casas decimais.
     * @param percent valor percentual a ser formatado
     * @return o valor com 2 casas decimais
     */
    public static double formatPercent(final double percent){
        final int decimalPlaces = 2;
        return BigDecimal.valueOf(percent).setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue();
    }
}
