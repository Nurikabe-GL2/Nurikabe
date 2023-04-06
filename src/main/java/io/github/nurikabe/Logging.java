package io.github.nurikabe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe utilitaire pour la journalisation
 */
public class Logging {
    private static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * Méthode getLogger qui retourne le {@link Logger} pour la classe qui appelle cette méthode
     *
     * @return le Logger pour la classe qui appelle cette méthode
     *
     * @see #getLogger(Class)
     */
    public static Logger getLogger() {
        return getLogger(WALKER.getCallerClass());
    }

    /**
     * Méthode getLogger qui retourne le {@link Logger} pour la classe donnée
     *
     * @return le Logger pour la classe donnée
     *
     * @see #getLogger()
     */
    public static Logger getLogger(Class<?> currentClass) {
        return LoggerFactory.getLogger(currentClass);
    }
}