package io.github.nurikabe;

/**
 * interface public 
 */
@FunctionalInterface
public interface ThrowingFunction<T, R> {
    R accept(T t) throws Exception;
}
