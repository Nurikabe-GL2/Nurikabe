package io.github.nurikabe.techniques;

import io.github.nurikabe.Coup;

import java.util.List;

/**
 * Classe représentant une liste de position donnée par la ou les techniques applicables
 */
public class PositionTechniques {
    private final Technique technique;
    private final List<Coup> coups;

    /**
     * Le constructeur de la classe PositionTechniques
     *
     * @param technique la technique applicable
     * @param coups     la liste des coordonnées des techniques applicables
     */
    PositionTechniques(Technique technique, List<Coup> coups) {
        this.technique = technique;
        this.coups = coups;
    }

    public Technique getTechnique() {
        return technique;
    }

    public List<Coup> getCoups() {
        return coups;
    }

    @Override
    public String toString() {
        return technique.toString();
    }
}
