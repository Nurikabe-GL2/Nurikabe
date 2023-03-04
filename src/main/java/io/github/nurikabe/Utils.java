package io.github.nurikabe;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Utils {
    /**
     * Retourne l'URL de la resource spécifiée, l'URL est relative à la classe donnée en paramètre.
     *
     * @param currentClass La classe qui appelle cette méthode
     * @param url          L'URL de la resource demandée
     *
     * @return L'URL de la resource.
     *
     * @throws IllegalArgumentException Si la resource n'existe pas
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

    /**
     * Charge le fichier FXML avec le nom donné, avec le contrôleur donné.
     *
     * <p>Le fichier devra être dans le package {@code view}.
     *
     * @param controller Le contrôleur de cette interface
     * @param name       Le nom du fichier FXML, sans le {@code .fxml}
     * @param <T>        Type du contrôleur
     *
     * @return Retourne le contrôleur donné en premier paramètre
     *
     * @throws IOException Si le chargement de l'interface à une erreur
     */
    public static <T extends Parent> T loadFxml(T controller, String name) throws IOException {
        final FXMLLoader loader = new FXMLLoader(Utils.getResource(Utils.class, "/view/" + name + ".fxml"));
        loader.setController(controller);
        loader.setRoot(controller);
        loader.load();

        return controller;
    }
}
