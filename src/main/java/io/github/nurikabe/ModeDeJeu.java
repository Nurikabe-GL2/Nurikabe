package io.github.nurikabe;

import javafx.scene.Node;
import javafx.scene.control.Toggle;

/**
 * Énumération représentant les trois modes de jeu proposés : classique, aventure et contre-la-montre
 */
public enum ModeDeJeu {
    CLASSIQUE("classique", "Classique", "classicToggle"), //Ne pas modifier les ID de mode
    AVENTURE("aventure", "Aventure", "adventureToggle"),
    CONTRE_LA_MONTRE("montre", "Contre la montre", "timeToggle");

    private final String nomMode;
    private final String descriptionMode;
    private final String idMode;

    /**
     * Constructeur de la classe ModesDeJeu
     *
     * @param nomMode le nom du mode de jeu à utiliser pendant les sauvegardes
     * @param idMode  l'id du mode de jeu {@link Toggle}
     */
    ModeDeJeu(String nomMode, String descriptionMode, String idMode) {
        this.nomMode = nomMode;
        this.descriptionMode = descriptionMode;
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

    public String getNomMode() {
        return nomMode;
    }

    public String getDescriptionMode() {
        return descriptionMode;
    }
}
