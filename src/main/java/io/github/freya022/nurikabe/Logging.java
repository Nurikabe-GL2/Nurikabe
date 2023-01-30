package io.github.freya022.nurikabe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
    private static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * Retourne le {@link Logger} pour la classe qui appelle cette méthode.
     *
     * @return Le Logger pour la classe qui appelle cette méthode
     *
     * @see #getLogger(Class)
     */
    public static Logger getLogger() {
        return getLogger(WALKER.getCallerClass());
    }

    /**
     * Retourne le {@link Logger} pour la classe donnée.
     *
     * @return le Logger pour la classe donnée
     *
     * @see #getLogger()
     */
    public static Logger getLogger(Class<?> currentClass) {
        return LoggerFactory.getLogger(currentClass);
    }
}
