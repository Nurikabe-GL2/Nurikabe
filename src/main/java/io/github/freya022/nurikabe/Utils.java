package io.github.freya022.nurikabe;

import java.net.URL;

public class Utils {
    public static URL getResource(Class<?> currentClass, String url) {
        final URL resource = currentClass.getResource(url);
        if (resource == null)
            throw new IllegalArgumentException("Unable to find resource '%s' from class '%s'".formatted(url, currentClass.getName()));

        return resource;
    }
}
