package io.github.nurikabe;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


@SuppressWarnings("resource")

/**
 * classe implémentant les outils de gestion des flux d'entrer sortie
 */
public class IOUtils {

    /**
     * implémente le path vers la racine
     */
    public static final Path ROOT_PATH;

    static {
        try {
            final var classesRootPath = Path.of(IOUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (Files.isDirectory(classesRootPath)) {
                ROOT_PATH = classesRootPath;
            } else {
                ROOT_PATH = FileSystems.newFileSystem(classesRootPath).getPath("/");
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getter du nom du fichier sans extension
     * @param path le chemin
     * @return une string le nom du fichier
     */
    @NotNull
    public static String getFileNameNoExtensions(@NotNull Path path) {
        final var fileName = path.getFileName().toString();
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
}
