package io.github.freya022.nurikabe;

import java.net.URL;

public class Utils {
    /**
     * Returns the URL of the specified resource, relative to the given class.
     *
     * @param currentClass The class this method is being called from
     * @param url          The URL string relative to the given class
     *
     * @return the URL to the requested resource
     *
     * @throws IllegalArgumentException If the resource does not exist
     */
    public static URL getResource(Class<?> currentClass, String url) {
        final URL resource = currentClass.getResource(url);
        if (resource == null)
            throw new IllegalArgumentException("Unable to find resource '%s' from class '%s'".formatted(url, currentClass.getName()));

        return resource;
    }
}
