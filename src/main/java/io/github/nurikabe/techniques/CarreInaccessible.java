package io.github.nurikabe.techniques;

import io.github.nurikabe.Niveau;

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