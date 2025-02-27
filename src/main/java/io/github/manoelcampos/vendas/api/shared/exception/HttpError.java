package io.github.manoelcampos.vendas.api.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Representa uma mensagem de erro HTTP com se devido código de stats.
 * @param status código de status HTTP
 * @param description Descrição do código de status HTTP
 * @param message Mensagem de erro
 * @author Manoel Campos
 */
public record HttpError(int status, String description, String message) {
    public HttpError(final ResponseStatusException ex) {
        this(ex.getStatusCode().value(), getHttpStatusName(ex), ex.getReason());
    }

    public HttpError(final HttpStatus status, final String message) {
        this(status.value(), status.name(), message);
    }

    private static String getHttpStatusName(final ResponseStatusException ex) {
        final String[] errorMsgParts = ex.getMessage().split(" ");
        return errorMsgParts.length == 0 ? "" : errorMsgParts[1];
    }
}
