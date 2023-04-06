package io.github.nurikabe;

/**
 * Énumération représentant les différentes difficultés du jeu.
 */
public enum Difficulte {
    FACILE("facile"),
    MOYEN("moyen"),
    DIFFICILE("difficile");

    private final String prefixeFichier;

    Difficulte(String prefixeFichier) {
        this.prefixeFichier = prefixeFichier;
    }

    public String getPrefixeFichier() {
        return prefixeFichier;
    }
}
