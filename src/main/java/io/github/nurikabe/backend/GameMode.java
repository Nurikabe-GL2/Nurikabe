package io.github.nurikabe.backend;

import javafx.scene.Node;
import javafx.scene.control.Toggle;

public enum GameMode {
    CLASSIQUE("classicToggle"),
    AVENTURE("adventureToggle"),
    CONTRE_LA_MONTRE("timeToggle");

    private final String fxId;

    GameMode(String fxId) {
        this.fxId = fxId;
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
