package io.github.nurikabe.techniques.avancee;

import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

/**
 * Comme dans l'exemple précédent, si nous supposons que le carré en surbrillance fait partie d'un mur,
 * une fois encore, une zone de mur de 2x2 sera créée, ce qui n'est pas autorisé.
 * Par conséquent, le carré en surbrillance doit faire partie d'un îlot et est marqué d'un point, comme indiqué dans le diagramme de droite.
 */
public class Avancee2 extends Technique {
    @Override
    protected String getIdentifiant() {
        return "avancee_2";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        return null; //TODO
    }
}