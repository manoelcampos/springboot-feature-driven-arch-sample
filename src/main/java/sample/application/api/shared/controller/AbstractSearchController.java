package sample.application.api.shared.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sample.application.api.shared.EntityRepository;
import sample.application.api.shared.model.AbstractBaseModel;
import sample.application.api.shared.service.AbstractCrudService;
import sample.application.api.shared.util.PathUtil;

import java.net.URI;

import static java.util.Objects.requireNonNullElse;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Classe base para a implementação de {@link RestController} que irão fornecer apenas operações de consulta
 * (a serem implementadas pelas classes filhas).
 * Cada classe filha deve incluir a anotação {@link RestController} e {@link RequestMapping}.
 * @param <T> tipo da entidade que o controller irá manipular
 *
 * @author Manoel Campos
 */
public class AbstractSearchController<T extends AbstractBaseModel, R extends EntityRepository<T>, S extends AbstractCrudService<T, R>> {
    /**
     * Caminho relativo do controller na anotação {@link RequestMapping}.
     */
    protected final String basePath;

    protected final S service;

    public AbstractSearchController(final S service) {
        this.service = service;
        this.basePath = findBasePath();
    }

    protected URI createdUri(final T entity) {
        return PathUtil.createUri(basePath, requireNonNullElse(entity.getId(), "").toString());
    }

    /**
     * Procura o valor da anotação {@link RequestMapping} na classe controller filha,
     * que representa o caminho do controller.
     *
     * @return caminho do controller
     */
    protected String findBasePath() {
        final var handlerClass = getClass();
        final var requestMapping = findAnnotation(handlerClass, RequestMapping.class);
        return requestMapping == null ? "" : requestMapping.value()[0];
    }

    protected ResponseStatusException newNotFoundException(final long id) {
        final var msg = "%s não encontrado(a) com id " + id;
        return newNotFoundException(msg);
    }

    /**
     * Cria uma exceção de recurso não encontrado.
     * @param msgFormat uma String contendo o formato da mensagem, podendo ter um %s para ser substituído pelo nome da entidade.
     *                  Se não existir %s, a mensagem será exibida como passada, sem formatação.
     * @return a exceção criada
     */
    protected ResponseStatusException newNotFoundException(final String msgFormat) {
        final var msg = msgFormat.contains("%s") ? msgFormat.formatted(service.getEntityClassName()) : msgFormat;
        return new ResponseStatusException(NOT_FOUND, msg);
    }

    protected R repository() {
        return service.repository;
    }
}
