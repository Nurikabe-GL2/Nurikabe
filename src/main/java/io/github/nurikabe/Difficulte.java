package io.github.nurikabe;

/**
 * Énumération représentant la difficulté de la grille
 */
public enum Difficulte {
    EASY("facile"),
    MEDIUM("moyen"),
    HARD("difficile");

    /**
     * variable d'instance représentant le nom d'affichage de la difficulté
     */
    private final String displayName;

    /**
     * Constructeur de la difficulté
     * @param displayName le nom d'affichage de la difficulté
     */
    Difficulte(String displayName) {
        this.displayName = displayName;
    }

    /**
     * getter du nom d'affichage de la difficulté
     * @return
     */
    public String getDisplayName() {
        return displayName;
    }
}
