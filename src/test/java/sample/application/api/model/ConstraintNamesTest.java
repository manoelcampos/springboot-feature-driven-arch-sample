package sample.application.api.model;

import jakarta.persistence.ForeignKey;
import jakarta.persistence.UniqueConstraint;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sample.application.api.ClassUtils;
import sample.application.api.config.ConstraintKeys;
import sample.application.api.shared.model.BaseModel;
import sample.application.api.shared.util.ConstraintViolation;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static sample.application.api.model.ConstraintNamesTest.ConstraintType.FOREIGN_KEY;
import static sample.application.api.model.ConstraintNamesTest.ConstraintType.UNIQUE_CONSTRAINT;
import static sample.application.api.shared.util.ConstraintViolation.FK_FORMAT_REGEX;
import static sample.application.api.shared.util.ConstraintViolation.UC_FORMAT_REGEX;
import static sample.application.api.shared.util.StringUtil.readableText;


/**
 * Verifica se as Foreign Keys e Unique Constraints definidas nas entidades {@link BaseModel}
 * estão no formato esperado.
 *
 * @author Manoel Campos
 */
class ConstraintNamesTest {
    enum ConstraintType {
        /**
         * @see ConstraintViolation#FK_FORMAT_REGEX
         */
        FOREIGN_KEY("fk_tabela_origem__tabela_destino", FK_FORMAT_REGEX),

        /**
         * @see ConstraintViolation#UC_FORMAT_REGEX
         * @see ConstraintKeys
         */
        UNIQUE_CONSTRAINT("uc_tabela_origem__campo1__campo2__campo_n___", UC_FORMAT_REGEX);

        /**
         * Uma descrição mais amigável do formato que o nome de uma constraint deve ter,
         * conforme a {@link #regex} associada a ela.
         * @see ConstraintViolation
         */
        private final String formatDescription;

        /**
         * Regex que identifica qual o formato do nome de uma constraint.
         */
        private final String regex;

        ConstraintType(final String formatDescription, final String regex) {
            this.formatDescription = formatDescription;
            this.regex = regex;
        }
    }

    /**
     * Verifica se o nome das FKs está conforme o formato esperado.
     */
    @Test
    void foreignKeyNames() {
        final var classes = ClassUtils.getClassesForPackage("");
        for (var aClass : classes) {
            ClassUtils.getFieldForeignKeyStream(aClass)
                      .map(ForeignKey::name)
                      .forEach(fk -> assertConstraintNameFormat(aClass, FOREIGN_KEY, fk));
        }
    }

    /**
     * Verifica se o nome das Unique Constraints (UCs) está de acordo com o formato esperado.
     * TODO: Needs to find all classes that implement the BaseModel interface
     */
    @Test @Disabled
    void uniqueConstraintNames() {
        final var classes = ClassUtils.getClassesForPackage(getClass().getPackageName());
        for (var aClass : classes) {
            ClassUtils.getUniqueConstraints(aClass)
                      .map(UniqueConstraint::name)
                      .forEach(uc -> assertConstraintNameFormat(aClass, UNIQUE_CONSTRAINT, uc));
        }
    }

    private static void assertConstraintNameFormat(
            final Class<?> aClass, final ConstraintType constraintType,
            final String constraintName)
    {
        final var pattern = ConstraintViolation.getPattern(constraintType.regex);
        assertTrue(pattern.matcher(constraintName).matches(), errorMsg(aClass, constraintType, constraintName));
    }

    /**
     * {@return uma mensagem de erro indicando que o nome da constraint não está no formato esperado}
     * @param aClass classe onde a violação da constraint ocorreu.
     * @param constraintType tipo de constraint (Foreign Key ou Unique Constraint)
     * @param constraintName nome da FK ou UC
     */
    private static String errorMsg(final Class<?> aClass, final ConstraintType constraintType, final String constraintName) {
        final var msg = "Nome da %s %s na classe %s não está no formato esperado %s";
        return msg.formatted(readableText(constraintType.name()), constraintName, aClass.getName(), constraintType.formatDescription);
    }
}
