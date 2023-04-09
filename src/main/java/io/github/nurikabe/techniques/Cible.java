package io.github.nurikabe.techniques;

import io.github.nurikabe.cases.Case;

/**
 * Représente une coordonnée ciblée par une technique
 *
 * @param x    La coordonnée X de la cible
 * @param y    La coordonnée Y de la cible
 * @param type Le type de case à appliquer à cette position, peut être un {@code .}, {@code b} ou {@code n}
 */
public record Cible(int x, int y, Case.Type type) {
}
