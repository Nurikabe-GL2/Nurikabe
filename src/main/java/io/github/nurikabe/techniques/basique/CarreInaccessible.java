package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

/**
 * Dans certains cas, une case ne peut appartenir à aucune île,
 * tout simplement parce qu'aucun indice ne peut l' « atteindre ».
 * Dans l'exemple ci-dessous, les deux cases ? en rouge sont trop éloignées des indices et ne peuvent faire partie d'aucune île.
 * Elles sont donc ombragées pour indiquer qu'elles doivent faire partie d'un mur.
 */
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