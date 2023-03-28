/**
 * Fichier IOUtils.java
 */

// Package GitHub
package io.github.nurikabe;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

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
}