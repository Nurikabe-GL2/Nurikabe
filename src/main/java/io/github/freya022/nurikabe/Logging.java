package io.github.freya022.nurikabe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
    private static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * Returns the {@link Logger} for the current class.
     *
     * @return the Logger for the class calling this method
     *
     * @see #getLogger(Class)
     */
    public static Logger getLogger() {
        return getLogger(WALKER.getCallerClass());
    }

    /**
     * Returns the {@link Logger} for the given class.
     *
     * @return the Logger for the given class
     *
     * @see #getLogger()
     */
    public static Logger getLogger(Class<?> currentClass) {
        return LoggerFactory.getLogger(currentClass);
    }
}
