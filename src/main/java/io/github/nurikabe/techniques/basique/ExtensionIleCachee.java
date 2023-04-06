package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

/**
 * Parfois, une île est trop grande pour tenir dans une zone donnée.
 * Dans l'énigme partiellement résolue ci-dessous, l'île de 12 ne peut pas tenir dans la zone du haut et doit donc s'étendre vers le bas,
 * comme indiqué par un point dans le diagramme de droite.
 * Notez que la case située à gauche de ce point ne peut appartenir à aucune île et qu'elle est donc ombragée pour faire partie d'un mur.
 */
public class ExtensionIleCachee extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_6";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        return null; //TODO
    }
}