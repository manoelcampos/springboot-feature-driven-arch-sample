package io.github.manoelcampos.vendas.api;

import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * Possui métodos utilitários para obter informações sobre as classes
 * Java para serem usadas nos testes.
 * @author Manoel Campos
 */
public class ClassUtils {
    private static final String SOURCE_DIR = "src/main/java/".replaceAll("/", File.separator);

    /**
     * Construtor privado para evitar que a classe seja instanciada.
     */
    private ClassUtils(){/**/}

    /**
     * Lê um arquivo .java até encontrar um chave, que indica o início de uma classe, interface, record ou enum.
     * Assim, antes disso estarão sendo obtidos apenas os imports para verificação.
     *
     * @param path caminho do arquivo .java a ser lido
     * @return o conteúdo antes da abertura de uma classe, interface, record ou enum
     * @throws IOException
     */
    public static String readJavaFileImports(final Path path) throws IOException {
        try (var lines = Files.lines(path)) {
            return lines.takeWhile(line -> !line.contains("{")).collect(joining(System.lineSeparator()));
        }
    }

    public static List<Class<?>> getClassesForPackage(final String packageName) {
        final String path = packageName.startsWith(SOURCE_DIR) ? packageName : SOURCE_DIR + packageName.replaceAll("\\.", File.separator);
        final var classes = new LinkedList<Class<?>>();
        try (final var directoryStream = Files.newDirectoryStream(Paths.get(path))) {
            for (final var pathElement : directoryStream) {
                if (Files.isRegularFile(pathElement) && pathElement.toString().endsWith(".java")) {
                    final var className = getClassName(packageName, pathElement);
                    try {
                        classes.add(Class.forName(className));
                    } catch (ClassNotFoundException e) {
                        // Ignora classes que não puderam ser carregadas
                    }
                } else if(Files.isDirectory(pathElement)){
                    classes.addAll(getClassesForPackage(getPackageNameFromPath(pathElement)));
                }
            }
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }

        return classes;
    }

    private static String getPackageNameFromPath(final Path path) {
        final String pathStr = path.toString();
        final String packageName = pathStr.contains(SOURCE_DIR) ? pathStr.replace(SOURCE_DIR, "") : pathStr;
        return packageName.replaceAll("[\\\\/]", ".");
    }

    private static @NotNull String getClassName(final String packageName, final Path pathElement) {
        return packageName + '.' + pathElement.getFileName().toString().replace(".java", "");
    }

    public static @NotNull Stream<ForeignKey> getFieldForeignKeyStream(final Class<?> aClass) {
        return getFieldsStream(aClass)
                .map(field -> AnnotationUtils.getAnnotation(field, JoinColumn.class))
                .filter(Objects::nonNull)
                .map(JoinColumn::foreignKey);
    }

    private static @NotNull Stream<Field> getFieldsStream(final Class<?> aClass) {
        return Arrays.stream(aClass.getDeclaredFields());
    }

    /**
     * Obtém as {@link UniqueConstraint} definidas na anotação {@link Table} da entidade.
     * @return
     */
    public static Stream<UniqueConstraint> getUniqueConstraints(final Class<?> modelClass){
        final var tableAnnotation = AnnotationUtils.findAnnotation(modelClass, Table.class);
        return tableAnnotation == null ?
                Stream.empty() :
                Arrays.stream(tableAnnotation.uniqueConstraints());
    }
}
