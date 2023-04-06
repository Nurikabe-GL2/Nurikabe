package io.github.nurikabe.techniques.avancee;

import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

/**
 * Examinons la case mise en évidence dans le puzzle partiellement résolu présenté dans le diagramme de gauche ci-dessous.
 * Si cette case fait partie d'un mur, les deux cases adjacentes deviennent inaccessibles et doivent également faire partie du mur,
 * créant ainsi une zone de mur de 2x2.
 * Puisque, selon les règles du Nurikabe, ceci n'est pas autorisé,
 * la case en surbrillance doit faire partie d'une île et est marquée d'un point dans le diagramme de droite.
 */
public class Avancee4 extends Technique {
    @Override
    protected String getIdentifiant() {
        return "avancee_4";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        return null; //TODO
    }

}