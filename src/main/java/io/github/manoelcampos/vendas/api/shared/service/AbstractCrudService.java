package io.github.manoelcampos.vendas.api.shared.service;

import io.github.manoelcampos.vendas.api.model.AbstractBaseModel;
import io.github.manoelcampos.vendas.api.shared.EntityRepository;
import io.github.manoelcampos.vendas.api.shared.controller.AbstractController;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementação base de um {@link CrudService} para
 * executar operações CRUD em um {@link AbstractController}.
 * @param <T> {@inheritDoc}
 * @param <R> {@inheritDoc}
 * @author Manoel Campos
 */
@Service
public abstract class AbstractCrudService<T extends AbstractBaseModel, R extends EntityRepository<T>> extends CrudService<T, R> {
    public AbstractCrudService(final R repository) {
        super(repository);
    }

    /**
     * <p><b>AVISO:</b> Não use o atributo diretamente, chame o {@link #getEntityClassName()}
     * para fazer lazy initialization.</p>
     */
    private String entityClassName;

    @Override
    public boolean deleteById(final long id) {
        return findById(id).map(this::deleteEntity).orElse(false);
    }

    @Override
    public Optional<T> findById(final long id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T save(final T entity) {
        return repository.saveAndFlush(entity);
    }

    public String getEntityClassName() {
        if (entityClassName != null) {
            return entityClassName;
        }

        final var typeParameters = repository.getClass().getTypeParameters();
        entityClassName = typeParameters.length == 0 ? "Objeto" : typeParameters[0].getClass().getSimpleName();
        return entityClassName;
    }
}
