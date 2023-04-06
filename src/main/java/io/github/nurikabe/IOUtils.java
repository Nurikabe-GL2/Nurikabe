package io.github.nurikabe;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Classe IOUtils implémentant les outils de gestion des flux d'entrées-sorties
 */
@SuppressWarnings("resource")
public class IOUtils {
    /**
     * Représente le dossier racine par rapport aux classes compilées,
     * c'est-à-dire le dossier contenant les fichiers {@code .class}.
     *
     * <p>Il peut aussi s'agir du fichier JAR du jeu.
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
     * Méthode getFileNameNoExtensions qui permet de récupérer du nom du fichier sans extension
     *
     * @param path le chemin jusqu'au fichier
     *
     * @return le nom du fichier sous forme de chaine de caractères
     */
    @NotNull
    public static String getFileNameNoExtensions(@NotNull Path path) {
        final var nomFichier = path.getFileName().toString();
        return nomFichier.substring(0, nomFichier.lastIndexOf('.'));
    }

    /**
     * Remplace l'extension dans ce chemin de fichier
     *
     * @param path         Le chemin de fichier
     * @param newExtension La nouvelle extension, sans le {@code .}
     *
     * @return Le nouveau chemin de fichier avec l'extension
     */
    @NotNull
    public static Path replaceExtension(@NotNull Path path, String newExtension) {
        return path.resolveSibling(getFileNameNoExtensions(path) + "." + newExtension);
    }

    @NotNull
    public static BufferedOutputStream newBufferedOutputStream(@NotNull Path path) throws IOException {
        return new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
    }

    @NotNull
    public static BufferedInputStream newBufferedInputStream(@NotNull Path path) throws IOException {
        return new BufferedInputStream(Files.newInputStream(path));
    }
}