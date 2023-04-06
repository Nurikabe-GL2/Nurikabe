package io.github.nurikabe;

import java.io.Serializable;

/**
 * Classe Coup représentant un coup joué servant pour le Undo Redo
 */
public record Coup(int x, int y) implements Serializable {
}