package io.github.nurikabe;

@FunctionalInterface
public interface ThrowingFunction<T, R> {
    R accept(T t) throws Exception;
}
