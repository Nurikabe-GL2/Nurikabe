package io.github.nurikabe.backend;

import javafx.scene.Node;
import javafx.scene.control.Toggle;

public enum GameMode {
    CLASSIQUE("classic", "classicToggle"),
    AVENTURE("adventure", "adventureToggle"),
    CONTRE_LA_MONTRE("timed", "timeToggle");

    private final String modeName;
    private final String fxId;

    /**
     * @param modeName Name of the game mode to be used when creating save directories
     * @param fxId     FX:ID to use when resolving game mode from a {@link Toggle}
     */
    GameMode(String modeName, String fxId) {
        this.modeName = modeName;
        this.fxId = fxId;
    }

    public String getModeName() {
        return modeName;
    }

    public String getFxId() {
        return fxId;
    }

    public static GameMode fromToggle(Toggle toggle) {
        final String toggleId = ((Node) toggle).getId();
        for (GameMode gameMode : values()) {
            if (gameMode.fxId.equals(toggleId)) {
                return gameMode;
            }
        }

        throw new IllegalArgumentException("Unknown game mode from FX:ID: " + toggleId);
    }
}
