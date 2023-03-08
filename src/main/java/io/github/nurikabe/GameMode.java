package io.github.nurikabe;

import javafx.scene.Node;
import javafx.scene.control.Toggle;

/**
 * Énumération public représentant le mode de jeu
 */
public enum GameMode {
    CLASSIQUE("classic", "classicToggle"),
    AVENTURE("adventure", "adventureToggle"),
    CONTRE_LA_MONTRE("timed", "timeToggle");

    /**
     * le nom d'affichage du mode de jeu
     */
    private final String modeName;
    
    /**
     * l'id du mode de jeu
     */
    private final String fxId;

    /**
     * Constructeur de l'énumération
     * @param modeName le nom du mode de jeux à utilisez pendant les sauvegardes
     * @param fxId     l'id du mode de jeu {@link Toggle}
     */
    GameMode(String modeName, String fxId) {
        this.modeName = modeName;
        this.fxId = fxId;
    }

    /**
     * getter du nom du mode de jeu
     * @return le nom du mode de jeu sous forme de chaine de caractère
     */
    public String getModeName() {
        return modeName;
    }

    /**
     * getter de l'id du mode de jeu
     * @return l'id du mode de jeu sous forme d'entier
     */
    public String getFxId() {
        return fxId;
    }

    /**
     * méthode renvoyant le mode de jeu
     * cette méthode peut renvoyer 
     * @param toggle 
     * @return le mode de jeu
     * @throws IllegalArgumentException exception renvoyé par la méthode en cas d'id incorrect
     */
    public static GameMode fromToggle(Toggle toggle) throws IllegalArgumentException {
        final String toggleId = ((Node) toggle).getId();
        for (GameMode gameMode : values()) {
            if (gameMode.fxId.equals(toggleId)) {
                return gameMode;
            }
        }

        throw new IllegalArgumentException("Unknown game mode from FX:ID: " + toggleId);
    }
}
