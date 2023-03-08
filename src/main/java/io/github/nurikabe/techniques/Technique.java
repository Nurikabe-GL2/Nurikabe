package main.java.io.github.nurikabe.techniques;

import io.github.nurikabe.Niveau;
/**
 * Interface des différentes techniques de jeu
 */
public interface Technique {
    PositionTechniques tester(Niveau grille);

    default boolean estValide(Niveau grille,int x, int y)
    {
        if(x<0||y<0||y>=grille.get_hauteur()||x>=grille.get_largeur())
            return false;
        return(grille.get(x).get_case().equals("b"));
    }
}
