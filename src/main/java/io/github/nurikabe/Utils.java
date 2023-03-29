/**
 * Fichier Utils.java
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Classe Utils
 */
public class Utils {
    /**
     * Retourne l'URL de la ressource spécifiée, l'URL est relative à la classe donnée en paramètre
     *
     * @param currentClass la classe qui appelle cette méthode
     * @param url          l'URL de la ressource demandée
     *
     * @return l'URL de la ressource
     *
     * @throws IllegalArgumentException si la ressource n'existe pas
     */
    public static URL getResource(Class<?> currentClass, String url) {
        final URL resource = currentClass.getResource(url);
        if (resource == null)
            throw new IllegalArgumentException("Unable to find resource '%s' from class '%s'".formatted(url, currentClass.getName()));

        return resource;
    }

    public static InputStream getResourceAsStream(Class<?> currentClass, String url) {
        final var resource = currentClass.getResourceAsStream(url);
        if (resource == null)
            throw new IllegalArgumentException("Unable to find resource '%s' from class '%s'".formatted(url, currentClass.getName()));

        return resource;
    }

    public static byte[] getResourceAsBytes(Class<?> currentClass, String url) throws IOException {
        try (InputStream stream = getResourceAsStream(currentClass, url)) {
            return stream.readAllBytes();
        }
    }

    public static String getResourceAsString(Class<?> currentClass, String url) throws IOException {
        return new String(getResourceAsBytes(currentClass, url), StandardCharsets.UTF_8);
    }

    /**
     * Charge le fichier FXML avec le nom donné, avec le contrôleur donné.
     * <p>Le fichier devra être dans le package {@code view}.
     *
     * @param controller le contrôleur de cette interface
     * @param name       le nom du fichier FXML, sans le {@code .fxml}
     * @param <T>        le type du contrôleur
     *
     * @return retourne le contrôleur donné en premier paramètre
     *
     * @throws IOException si le chargement de l'interface à une erreur
     */
    public static <T extends Parent> T loadFxml(T controller, String name) throws IOException {
        final FXMLLoader loader = new FXMLLoader(Utils.getResource(Utils.class, "/view/" + name + ".fxml"));
        loader.setController(controller);
        loader.setRoot(controller);
        loader.load();
        return controller;
    }
}