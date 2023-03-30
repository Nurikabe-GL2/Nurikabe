/**
 * Fichier Difficultes.java représentant les trois difficultés proposées par notre jeu
 */

// Package GitHub
package io.github.nurikabe;

/**
 * Classe de type Enum représentant les différentes difficultés du jeu
 */
public enum Difficulte {
    FACILE("Facile", "facile"),
    MOYEN("Moyen", "moyen"),
    DIFFICILE("Difficile", "difficile");

    /**
     * Variable d'instance représentant le nom d'affichage des difficultés
     */
    private final String nomDiff;
    private final String prefixeFichier;

    /**
     * Constructeur de la difficulté
     *
     * @param nomDiff le nom d'affichage de la difficulté
     */
    Difficulte(String nomDiff, String prefixeFichier) {
        this.nomDiff = nomDiff;
        this.prefixeFichier = prefixeFichier;
    }

    /**
     * Méthode recupNomDifficulte qui renvoie le nom de la difficulté actuelle
     *
     * @return le nom de la difficulté
     */
    public String recupNomDifficulte() {
        return nomDiff;
    }

    public String getPrefixeFichier() {
        return prefixeFichier;
    }
}
