package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

/**
 * Dans l'énigme partiellement résolue ci-dessous, l'île de 3 a été complétée comme le montre la zone en surbrillance.
 * Nous pouvons maintenant entourer cette île de murs adjacents horizontalement et verticalement, comme le montre l'image de droite.
 */
public class EntourerIleTerminee extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_8";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        return null; //TODO
    }
}