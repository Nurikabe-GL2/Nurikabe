package io.github.nurikabe.techniques;

import java.util.Arrays;
import java.util.List;

/**
 * Classe représentant une liste de position donnée par la ou les techniques applicables
 */
public class PositionTechniques {
    private final Technique technique;
    private final List<Cible> cibles;

    /**
     * Le constructeur de la classe PositionTechniques
     *
     * @param technique la technique applicable
     * @param cibles    la liste des cases à jouer
     */
    PositionTechniques(Technique technique, List<Cible> cibles) {
        this.technique = technique;
        this.cibles = cibles;
    }

    PositionTechniques(Technique technique, Cible... cibles) {
        this(technique, Arrays.asList(cibles));
    }

    public Technique getTechnique() {
        return technique;
    }

    public List<Cible> getCibles() {
        return cibles;
    }

    @Override
    public String toString() {
        return technique.toString();
    }
}
