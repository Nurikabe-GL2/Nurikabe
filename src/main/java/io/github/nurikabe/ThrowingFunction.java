/**
 * Fichier ThrowingFunction.java
 */

// Package GitHub
package io.github.nurikabe;

/**
 * Interface ThrowingFunction publique 
 */
@FunctionalInterface
public interface ThrowingFunction<T, R> {
   R accept(T t) throws Exception;
}