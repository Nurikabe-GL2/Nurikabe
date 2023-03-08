package main.java.io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.Niveau;
/**
 * Interface des différentes techniques de jeu
 */
public interface Technique {
    PositionTechniques tester(Niveau grille);

    /**
     * Méthode pour tester si une case est valide ou non (indice valide et case blanche)
     * @param grille la grille courante
     * @param x la coordonnée x
     * @param y la coordonnée y
     * @return true si elle est valide ou false
     */
    default boolean estValide(Niveau grille,int x, int y)
    {
        if(x<0||y<0||y>=grille.get_hauteur()||x>=grille.get_largeur())
            return false;
        return(grille.get(x).get_case().equals("b"));
    }

    default boolean estValideCaseNombreEspace(Niveau grille,int x, int y)
    {
        if(x<0||y<0||y>=grille.get_hauteur()||x>=grille.get_largeur())
            return false;
        return(grille.get(x).get_case() instanceof CaseNombre);
    }
}
