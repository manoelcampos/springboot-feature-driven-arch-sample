package sample.application.api.shared.service;

import org.springframework.stereotype.Service;
import sample.application.api.shared.EntityRepository;
import sample.application.api.shared.controller.AbstractController;
import sample.application.api.shared.model.AbstractBaseModel;

import java.util.List;
import java.util.Optional;

/**
 * Fornece um contrato para implementação de {@link Service}s que
 * executam operações CRUD em um {@link AbstractController}.
 * Encapsula todas as regras de negócio, deixando o controller
 * lidar apenas com a camada HTTP
 * (como response, request, códigos de status, redirect, etc).
 * @param <T> tipo da entidade que o service irá manipular
 * @param <R> tipo do repositório que acesso os dados da entidade no banco
 *
 * @author Manoel Campos
 */
public interface CrudService<T extends AbstractBaseModel, R extends EntityRepository<T>> {
    boolean deleteById(long id);

    Optional<T> findById(long id);

    List<T> findAll();

    T save(T entity);

    /**
     * {@return o nome da entidade que o service manipula}
     * Tal classe é a {@link AbstractBaseModel} definida de forma genérica
     * na declaração de classes que implementam esta interface.
     */
    String getEntityClassName();
}
