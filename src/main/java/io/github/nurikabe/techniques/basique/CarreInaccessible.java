package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

public class CarreInaccessible extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_10";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        return null; //TODO
    }
}