package io.github.nurikabe.techniques.avancee;

import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

/**
 * La case surlignée sous le 2 dans le diagramme de gauche ci-dessous, comme toute autre case dans le Nurikabe,
 * peut être soit une île, soit un mur.
 * Supposons qu'elle fasse partie d'une île, ce qui signifie que l'île du 2 peut être complétée et entourée de murs comme indiqué dans le diagramme du centre.
 * Cependant, cela créera une zone de mur de 2x2, ce qui n'est pas autorisé.
 * Par conséquent, le carré sous le 2 doit faire partie d'un mur, comme le montre le diagramme de droite.
 */
public class Avancee1 extends Technique {
    @Override
    protected String getIdentifiant() {
        return "avancee_1";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        return null; //TODO
    }
}