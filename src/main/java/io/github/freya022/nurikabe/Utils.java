package io.github.freya022.nurikabe;

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
}
