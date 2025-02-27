package io.github.manoelcampos.vendas.api.shared.validator;

import io.github.manoelcampos.vendas.api.model.AbstractBaseModel;
import org.springframework.validation.Errors;

/**
 * Classe base para implementação de {@link CustomValidator}s.
 * @author Manoel Campos
 */
public abstract class AbstractCustomValidator<T extends AbstractBaseModel> extends CustomValidator<T> {
    @Override
    protected abstract Class<T> getSupportedClass();

    @SuppressWarnings("unchecked") @Override
    public final void validate(final Object target, final Errors errors) {
        final var supportedClass = getSupportedClass().getSimpleName();
        final var targetClass = target.getClass();
        if(!supports(targetClass)) {
            final var validatorClass = getClass().getSimpleName();
            final var msg = "%s só permite validação de objetos do tipo %s. Tentando validar %s".formatted(validatorClass, supportedClass, targetClass.getSimpleName());
            throw new UnsupportedOperationException(msg);
        }

        validateInternal((T)target, errors);
    }

    /**
     * Implementa as regras específicas de validação para a entidade.
     * @param target objeto a ser validado
     * @param errors erros capturados na validação
     * @see #validate(Object, Errors)
     */
    protected abstract void validateInternal(T target, Errors errors);
}
