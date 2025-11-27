package sample.application.api.shared.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sample.application.api.shared.EntityRepository;
import sample.application.api.shared.model.AbstractBaseModel;
import sample.application.api.shared.service.AbstractCrudService;
import sample.application.api.shared.validator.CustomValidator;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CONFLICT;
import static sample.application.api.shared.controller.RestExceptionHandler.newConflictException;

/// Classe base para a implementação de [RestController] que fornecem todas as operações CRUD
/// e podem trabalhar tanto com entidades (classes model) quanto com DTOs.
/// Se um DTO for passado no parâmetro D, os métodos [#insert(AbstractBaseModel)]
/// e [#update(long,AbstractBaseModel)] irão receber um DTO no lugar de uma entidade correspondente.
///
/// Cada classe filha deve incluir a anotação [RestController] e [RequestMapping].
///
/// @param <T> tipo da entidade que o controller irá manipular
/// @param <R> tipo do repositório que acesso os dados da entidade no banco
/// @author Manoel Campos
public abstract class AbstractController<T extends AbstractBaseModel, R extends EntityRepository<T>, S extends AbstractCrudService<T, R>> extends AbstractSearchController<T, R, S> {
    /**
     * Validador customizado para a entidade manipulada pelo controller.
     * O validador é opcional, pois nem sempre é necessário
     * realizar validações customizadas para a entidade.
     * Se nenhuma classe validator para a entidade for definida, uma instância de {@link CustomValidator} é usada.
     */
    protected final CustomValidator<T> validator;

    public AbstractController(final S service) {
        super(service);
        this.validator = new CustomValidator<>();
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<T> delete(@Valid @Min(1) @PathVariable final long id) {
        if (service.deleteById(id))
            return ResponseEntity.noContent().build();

        throw newNotFoundException(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<T> findById(@Min(1) @PathVariable final long id) {
        return service.findById(id)
                      .map(ResponseEntity::ok)
                      .orElseThrow(() -> newNotFoundException(id));
    }

    @GetMapping
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Insere um objeto como um novo registro no banco de dados.
     *
     * @param obj objeto que pode ser uma entidade do tipo T ou um DTORecord.
     * @return
     */
    @PostMapping
    @Transactional
    public ResponseEntity<T> insert(@Valid @RequestBody T obj) {
        validate(obj);
        try {
            obj.id = null;
            obj = service.save(obj);
            return ResponseEntity.created(createdUri(obj)).body(obj);
        } catch (final ConstraintViolationException e) {
            throw newConflictException(e.getMessage());
        }
    }

    /**
     * Atualiza um registro no banco de dados a partir dos dados de um objeto.
     *
     * @param obj objeto que pode ser uma entidade do tipo T ou um DTORecord.
     * @return
     */
    @PutMapping("{id}")
    @Transactional
    public void update(@Valid @Min(1) @PathVariable final long id, @Valid @RequestBody final T obj) {
        if (!obj.isSameId(id)) {
            final var msg = "O ID informado (%d) não corresponde com o ID do %s (%d)".formatted(id, service.getEntityClassName(), obj.getId());
            throw newConflictException(msg);
        }

        validate(obj);

        try {
            service.save(obj);
        } catch (final ConstraintViolationException e) {
            throw newConflictException(e.getMessage());
        }
    }

    private void validate(final T entity) {
        final var errors = new BindException(entity, entity.getClass().getSimpleName());
        validator.validate(entity, errors);
        final var errorsStr = errors.getAllErrors()
                                    .stream()
                                    .map(DefaultMessageSourceResolvable::getCode)
                                    .collect(Collectors.joining(";\n"));
        if (errors.hasErrors())
            throw new ResponseStatusException(CONFLICT, errorsStr);
    }
}
