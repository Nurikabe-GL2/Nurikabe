package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

/**
 * Selon les règles du Nurikabe, tous les murs doivent former un seul chemin continu.
 * Si on regarde le mur isolé dans le diagramme de gauche ci-dessous,
 * la seule façon de le relier aux autres murs est de l'agrandir avec trois carrés sur sa droite,
 * comme indiqué dans le diagramme de droite.
 */
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