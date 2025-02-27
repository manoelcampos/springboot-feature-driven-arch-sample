package io.github.manoelcampos.vendas.api.shared.util;

import java.net.URI;
import java.nio.file.Paths;

/**
 * Funções utilitários para manipulação de caminhos (paths, URIs).
 * @author Manoel Campos
 */
public class PathUtil {

    /**
     * Cria uma URI a partir da concatenação de caminhos
     * @param first caminho inicial
     * @param others outras partes a serem concatenadas
     * @return a URI completa (concatenada)
     */
    public static URI createUri(final String first, final String ...others) {
        return URI.create(concat(first, others));
    }

    /**
     * Concatena URIs com /
     * @param first caminho inicial
     * @param others outras partes a serem concatenadas
     * @return o caminho completo (concatenado)
     */
    public static String concat(final String first, String ...others){
        final String path = Paths.get(first, others).toString();
        // Troca \ por / quando estiver no Windows, pois está lidando com URIs não caminhos locais. Em outros SOs não causa nenhum efeito
        return path.replaceAll("\\\\", "/");
    }
}
