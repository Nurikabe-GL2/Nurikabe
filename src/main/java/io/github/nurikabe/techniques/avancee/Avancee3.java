package io.github.nurikabe.techniques.avancee;

import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

/**
 * Si la case surlignée dans le diagramme de gauche ci-dessous fait partie d'une île,
 * alors la case sous le 2 doit faire partie d'un mur.
 * Cependant, cette partie de mur ne pourra pas être reliée à d'autres murs du puzzle, comme le montre le diagramme du centre.
 * Le carré en surbrillance doit donc faire partie d'un mur et est ombragé comme indiqué dans le diagramme de droite.
 */
public class Avancee3 extends Technique {
    @Override
    protected String getIdentifiant() {
        return "avancee_3";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        return null; //TODO
    }
}