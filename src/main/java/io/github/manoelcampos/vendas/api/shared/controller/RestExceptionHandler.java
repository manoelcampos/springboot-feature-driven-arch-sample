package io.github.manoelcampos.vendas.api.shared.controller;

import io.github.manoelcampos.vendas.api.model.AbstractBaseModel;
import io.github.manoelcampos.vendas.api.shared.exception.HttpError;
import io.github.manoelcampos.vendas.api.shared.util.ConstraintViolation;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

import static io.github.manoelcampos.vendas.api.shared.util.ConstraintViolation.findUniqueConstraintMessage;
import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * Captura exceções específicas para retornar um {@link HttpError}
 * com uma mensagem amigável.
 *
 * @author Manoel Campos
 */
@ControllerAdvice
public class RestExceptionHandler {
    private static final Logger log = LogManager.getLogger(RestExceptionHandler.class);

    /**
     * Captura exceções {@link ResponseStatusException}
     * para permitir emitir uma mensagem junto com o código de status.
     * Como os {@link AbstractController} são genéricos, não é possível retornar
     * uma String no corpo da mensagem, mas sim um objeto {@link AbstractBaseModel}.
     * Desta forma, a exceção precisa ser capturada aqui e retornada uma {@link ResponseEntity}
     * String com a mensagem de erro personalizada.
     *
     * @param ex exceção capturada
     * @return {@link ResponseEntity} com a mensagem de erro personalizada
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<HttpError> handleResponseStatusException(final ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(new HttpError(ex));
    }

    /**
     * Captura exceções {@link DataIntegrityViolationException}
     * para verificar se foram lançadas devido a uma violação
     * de Foreign Key
     * @param ex exceção lançada
     * @return {@link ResponseEntity} com a mensagem de erro personalizada
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<HttpError> handleDataIntegrityViolationException(final DataIntegrityViolationException ex) {
        final var status = CONFLICT;
        final var msg = ConstraintViolation
                            .findForeignKeyMessage(ex)
                            .or(() -> findUniqueConstraintMessage(ex))
                            .orElse("Erro ao executar operação");

        return ResponseEntity.status(status).body(new HttpError(status, msg));
    }

    /**
     * Captura um erro de validação do Hibernate Validator que ocorre
     * quando um parâmetro de um endpoint em um controller é anotado com {@link Valid}.
     *
     * @param ex exceção ocorrida
     * @return {@link ResponseEntity} com a mensagem de erro personalizada
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<HttpError> handleResponseStatusException(final BindException ex) {
        final String errorMessages = getValidationErrorMessages(ex);
        var statusEx = newConflictException(errorMessages);
        return ResponseEntity.status(statusEx.getStatusCode()).body(new HttpError(statusEx));
    }

    private static String getValidationErrorMessages(final BindException ex) {
        return ex.getBindingResult().getAllErrors().stream()
                 .map(RestExceptionHandler::getValidationMessage)
                 .collect(Collectors.joining(". "));
    }

    private static String getValidationMessage(final ObjectError error) {
        final var target = error instanceof FieldError fieldError ? fieldError.getField() : error.getObjectName();
        return "%s %s".formatted(target, error.getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpError> handleResponseStatusException(final Exception ex) {
        final var msg = "Ocorreu um erro inesperado";
        log.error(msg, ex);
        final var status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(new HttpError(status.value(), status.name(), msg));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<HttpError> handleIllegalStateException(final IllegalStateException ex) {
        return validationException(ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpError> handleIllegalArgumentException(final IllegalArgumentException ex) {
        return validationException(ex);
    }

    private static ResponseEntity<HttpError> validationException(final Exception ex) {
        final var msg = ex.getMessage();
        log.error("Erro de validação", ex);
        final var status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(new HttpError(status.value(), status.name(), msg));
    }

    public static ResponseStatusException newConflictException(final String msg) {
        return new ResponseStatusException(CONFLICT, msg);
    }
}
