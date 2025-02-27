package io.github.manoelcampos.vendas.api.shared.validator;

import io.github.manoelcampos.vendas.api.model.AbstractBaseModel;
import io.github.manoelcampos.vendas.api.shared.service.CrudService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Define um contrato para implementação de {@link Validator}s personalizados.
 * É definida como uma classe concreta (no lugar de uma interface)
 * apenas para permitir injeção de dependência de um validator para uma {@link AbstractBaseModel}
 * específica, quando uma subclasse desta aqui não for criada,
 * indicando que a entidade não possui validações customizadas.
 *
 * <p>Nestes casos, o Spring pode instanciar um objeto desta classe (já que ela é concreta).
 * Com isto, não é preciso armazenar
 * null em um objeto validator de um {@link CrudService}
 * e assim não é preciso escrever verificações para evitar NullPointerExceptions.
 * Mesmo que não exista uma classe para validar uma determinada entity,
 * a classe service de tal entity terá uma instância de um validator padrão
 * (mas que não realiza nenhuma validação personalizada).
 * </p>
 *
 * <p><b>AVISO</b>: Subclasses concretas não devem ser criadas a partir desta classe,
 * mas sim de {@link AbstractCustomValidator}, pois tal classe fornecerá
 * parte da implementação.</p>
 *
 * @author Manoel Campos
 */
@Component @RequestScope
public class CustomValidator<T extends AbstractBaseModel> implements Validator {
    /**
     * Indica qual o tipo de {@link AbstractBaseModel} este validator pode validar.
     * @return
     */
    @SuppressWarnings("unchecked")
    protected Class<T> getSupportedClass(){
        // O método deve ser sobrescrito pelas subclasses para usar um tipo específico de entity.
        return  (Class<T>) AbstractBaseModel.class;
    }

    @Override
    public final boolean supports(final Class<?> clazz) {
        return getSupportedClass().isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        // Implementação vazia que não realiza qualquer validação personalizada. Subclasses devem sobrescrever o método.
    }
}
