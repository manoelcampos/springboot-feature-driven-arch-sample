/**
 * Classes base para implementação de serviços a serem usadas pelos controllers REST.
 *
 * <p>
 * Crie uma classe de serviço para cada classe {@link io.github.manoelcampos.vendas.api.model.AbstractBaseModel} do sistema.
 * Tal classe deve ser criada no subpacote dentro de {@link saneaplan.api.feature},
 * contendo as classes para a implementação de uma determinada funcionalidade do sistema
 * (como as classes service, repository e controller para um determinado cadatro).
 * Sem a classe service específica, você pode receber um erro "AbstractRestController required a bean of type 'CrudService' that could not be found",
 * pois nenhuma classe que estenda {@code CrudService<T extends AbstractEntity>} será encontrada.
 * <p>
 */
package io.github.manoelcampos.vendas.api.shared.service;

