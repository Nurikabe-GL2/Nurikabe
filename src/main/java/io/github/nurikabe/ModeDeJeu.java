/**
 * Fichier ModesDeJeu.java représentant les trois modes de jeu proposés : classique, aventure et contre-la-montre
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX

import javafx.scene.Node;
import javafx.scene.control.Toggle;

/**
 * Énumération public représentant le mode de jeu
 */
public enum ModeDeJeu {
    CLASSIQUE("classique", "classicToggle"), //Ne pas modifier les ID de mode
    AVENTURE("aventure", "adventureToggle"),
    CONTRE_LA_MONTRE("montre", "timeToggle");

    /**
     * Variable d'instance représentant le nom du mode de jeu
     */
    private final String nomMode;

    /**
     * Variable d'instance représentant d'id du mode de jeu
     */
    private final String idMode;

    /**
     * Constructeur de la classe ModesDeJeu
     *
     * @param nomMode le nom du mode de jeu à utiliser pendant les sauvegardes
     * @param idMode  l'id du mode de jeu {@link Toggle}
     */
    ModeDeJeu(String nomMode, String idMode) {
        this.nomMode = nomMode;
        this.idMode = idMode;
    }

    /**
     * Méthode fromToggle renvoyant le mode de jeu
     *
     * @return le mode de jeu
     *
     * @throws IllegalArgumentException exception renvoyée par la méthode en cas d'id incorrect
     */
    public static ModeDeJeu fromToggle(Toggle toggle) throws IllegalArgumentException {
        final String toggleId = ((Node) toggle).getId();
        for (ModeDeJeu jeuMode : values()) {
            if (jeuMode.idMode.equals(toggleId)) {
                return jeuMode;
            }
        }
        throw new IllegalArgumentException("Mot de jeu inconnu pour l'ID suivant : " + toggleId);
    }

    /**
     * Méthode recupNomMode qui permet de récupérer le nom du mode de jeu
     *
     * @return le nom du mode de jeu sous forme de chaine de caractère
     */
    public String recupNomMode() {
        return nomMode;
    }

    /**
     * Méthode recupIdMode qui permet de récupérer l'id du mode de jeu
     *
     * @return l'id du mode de jeu sous forme de chaine de caractère
     */
    public String recupIdMode() {
        return idMode;
    }
}
