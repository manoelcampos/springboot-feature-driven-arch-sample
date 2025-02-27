package io.github.manoelcampos.vendas.api.model;

import io.github.manoelcampos.vendas.api.ClassUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Verifica se as classes do pacote {@link saneaplan.api.model} usam a anotação {@link org.jetbrains.annotations.Nullable}
 * para identificar quando um atributo pode ser nulo, no lugar de outras anotações possíveis como {@link jakarta.annotation.Nullable}
 * ou {@link org.springframework.lang.Nullable}.
 * Isto irá garantir consistência no tipo de anotação usado no projeto.
 * Adicionalmente, o typescript-generator irá gerar tais classes no arquivo models.ts
 * e ele identifica quais campos podem ser nulos por meio de algumas anotações Nullable específicas.
 * No entanto, ele não suporta todas as anotações possíveis que podem ser usadas para tal fim.
 * Para evitar erros obscuros no build ao executar o typescript-generator,
 * tal classe de testes foi criada para garantir que apenas a anotação {@link org.jetbrains.annotations.Nullable}
 * seja usada. Caso contrário, exibe uma mensagem de erro mais clara.
 *
 * <p>Esta classe de teste precisa estar no mesmo pacote das classes que deseja procurar pelas anotações
 * Nullable.</p>
 *
 * @author Manoel Campos
 * @see <a href="https://github.com/vojtechhabarta/typescript-generator">typescript-generator</a>
 */
public class NullableAnnotationImportTest {
    private static final List<String> nonAllowedNullableAnnotations = List.of("org.springframework.lang.Nullable", "jakarta.annotation.Nullable");
    private static final String allowedAnnotation = "org.jetbrains.annotations.Nullable";

    @Test
    public void nullableAnnotationImport() throws IOException {
        final var packageName = getClass().getPackageName();
        final var classes = ClassUtils.getClassesForPackage(packageName);

        for (final var clazz : classes) {
            final var path = Paths.get("src/main/java", clazz.getName().replace('.', '/') + ".java");
            final var content = ClassUtils.readJavaFileImports(path);
            nonAllowedNullableAnnotations.forEach(annotation -> {
                assertFalse(
                    content.contains("import %s;".formatted(annotation)),
                    () -> "Classe %s não deve importar a anotação %s mas %s".formatted(clazz.getName(), annotation, allowedAnnotation));
            });
        }
    }

}
