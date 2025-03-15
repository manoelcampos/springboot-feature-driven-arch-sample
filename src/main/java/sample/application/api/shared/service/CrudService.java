package sample.application.api.shared.service;

import org.springframework.stereotype.Service;
import sample.application.api.shared.EntityRepository;
import sample.application.api.shared.controller.AbstractController;
import sample.application.api.shared.model.BaseModel;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

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
public abstract class CrudService<T extends BaseModel, R extends EntityRepository<T>> {
    public final R repository;
    public final String entityClassName;

    public CrudService(final R repository) {
        this.repository = repository;
        this.entityClassName = findEntityClassName();
    }

    public Optional<T> findById(long id) {
        return repository.findById(id);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public T save(T entity) {
        return repository.saveAndFlush(entity);
    }

    private String findEntityClassName() {
        final var typeParameters = repository.getClass().getTypeParameters();
        return typeParameters.length == 0 ? "Objeto" : typeParameters[0].getClass().getSimpleName();
    }

    /**
     * Obtém um {@link Supplier} de {@link NoSuchElementException} com a mensagem passada como parâmetro.
     * Tal método pode ser chamado em operações como {@link Optional#orElseThrow(Supplier)}
     * ao chamar métodos como {@link EntityRepository#findById(Object)} e qualquer
     * outro que retorne um Optional.
     * @param msg mensagem de erro a ser exibida quando a exceção retornada pelo Supplier for lançada
     * @return o supplier de {@link NoSuchElementException}
     */
    protected Supplier<NoSuchElementException> notFoundSupplier(final String msg) {
        return () -> new NoSuchElementException(msg);
    }

    public boolean deleteById(final long id) {
        return findById(id).map(this::deleteEntity).orElse(false);
    }

    protected boolean deleteEntity(final T entity){
        repository.delete(entity);
        return true;
    }
}
