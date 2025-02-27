package io.github.manoelcampos.vendas.api.shared.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Manoel Campos
 */
public final class ListUtil {
    /** Construtor privado para impedir instanciar a classe */
    private ListUtil() {/**/}

    /**
     * Cria um LinkedList mutável a partir dos valores passados por parâmetro
     * @param items itens a serem adicionados na lista
     * @return o novo LinkedList
     * @param <T> tipo dos elementos da lista
     */
    @SafeVarargs
    public static <T> List<T> of(final T ...items){
        final var list = new LinkedList<T>();
        Collections.addAll(list, items);
        return list;
    }

    /**
     * Adiciona um item a uma lista e retorna a própria lista.
     * @param list lista para adicionar um item
     * @param item item a ser adicioknado
     * @return a lista passada por parâmetro
     * @param <T> tipo dos elementos da lista
     */
    public static <T> List<T> add(final List<T> list, final T item){
        list.add(item);
        return list;
    }
}
