package io.github.freya022.nurikabe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
    private static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public static Logger getLogger() {
        return getLogger(WALKER.getCallerClass());
    }

    public static Logger getLogger(Class<?> currentClass) {
        return LoggerFactory.getLogger(currentClass);
    }
}
