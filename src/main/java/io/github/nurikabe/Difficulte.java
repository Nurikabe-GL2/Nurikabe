/**
 * Fichier Difficultes.java représentant les trois difficultés proposées par notre jeu
 */

// Package GitHub
package io.github.nurikabe;

/**
 * Classe de type Enum représentant les différentes difficultés du jeu
 */
public enum Difficulte {
    FACILE("Facile"),
    MOYEN("Moyen"),
    DIFFICILE("Difficile");

    /**
     * Variable d'instance représentant le nom d'affichage des difficultés
     */
    private final String nomDiff;

    /**
     * Constructeur de la difficulté
     *
     * @param nomDiff le nom d'affichage de la difficulté
     */
    Difficulte(String nomDiff) {
        this.nomDiff = nomDiff;
    }

    /**
     * Méthode recupNomDifficulte qui renvoie le nom de la difficulté actuelle
     *
     * @return le nom de la difficulté
     */
    public String recupNomDifficulte() {
        return nomDiff;
    }
}
