package sample.application.api.shared.exception;

/**
 * @author Manoel Campos
 */
public class FieldValidationException extends RuntimeException {
    private final String fieldName;

    /**
     * Cria uma exceção de validação de campo.
     * @param message mensagem de erro de validação
     * @param fieldName nome do campo sendo validado
     */
    public FieldValidationException(final String message, final String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }
}
