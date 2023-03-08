package io.github.nurikabe.techniques;

import io.github.nurikabe.Coup;

import java.util.List;

/**
 * Classe représentant une liste de position donnée par la ou les techniques applicables
 */
public class PositionTechniques {
    List<Coup> coups;
    /**
     * Le constructeur de la classe PositionTechniques 
     * @param coups la liste des coordonnées des techniques applicables
     */
    PositionTechniques(List<Coup> coups)
    {
        this.coups = coups;
    }
}
