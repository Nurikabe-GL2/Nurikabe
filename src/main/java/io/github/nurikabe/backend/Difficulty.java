package io.github.nurikabe.backend;

public enum Difficulty {
    EASY("Facile"),
    MEDIUM("Moyen"),
    HARD("Difficile");

    private final String displayName;

    Difficulty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
