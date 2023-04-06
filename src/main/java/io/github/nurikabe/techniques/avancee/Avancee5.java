package io.github.nurikabe.techniques.avancee;

import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

/**
 * Vous trouverez ci-dessous une section d'un puzzle plus grand partiellement résolu dans lequel l'île des 6 a besoin d'une dernière case pour être complétée.
 * Si nous supposons que cette case est celle qui est surlignée en rouge dans le diagramme de gauche ci-dessous,
 * la case située en dessous doit faire partie d'un mur et la case située à gauche de ce mur doit faire partie d'une île pour éviter une zone de mur de 2x2.
 * Tout ceci, comme le montre le diagramme du centre, va complètement entourer le mur à droite du 6 en éliminant la continuité du mur dans toutes les directions.
 * Par conséquent, le dernier carré de l'îlot de 6 doit être vers la droite et cet îlot peut être complété comme indiqué dans le diagramme de droite.
 */
public class Avancee5 extends Technique {
    @Override
    protected String getIdentifiant() {
        return "avancee_5";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        return null; //TODO
    }
}