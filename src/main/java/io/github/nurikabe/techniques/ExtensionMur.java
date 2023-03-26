package io.github.nurikabe.techniques;

import io.github.nurikabe.Niveau;

public class ExtensionMur extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_2";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        return null; //TODO
    }
}