package io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.Niveau;
import org.jetbrains.annotations.Nullable;

/**
 * Interface des différentes techniques de jeu
 */
public abstract class Technique {
    @Nullable
    public abstract PositionTechniques tester(Niveau grille);

    /**
     * Méthode pour tester si une case est valide ou non (indice valide et case blanche)
     *
     * @param grille la grille courante
     * @param x      la coordonnée x
     * @param y      la coordonnée y
     * @return true si elle est valide ou false
     */
    protected boolean estValide(Niveau grille, int x, int y) {
        if (estCoordonneeValide(grille, x, y))
            return false;
        return (grille.etat_case(x, y).equals("b"));
    }

    protected boolean estUnNombre(Niveau grille, int x, int y) {
        try {
            if (!estCoordonneeValide(grille, x, y)) return false;

            Integer.parseInt(grille.etat_case(x, y));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean estValideCaseNombreEspace(Niveau grille, int x, int y) {
        if (estCoordonneeValide(grille, x, y))
            return false;
        return (grille.get_case(x, y) instanceof CaseNombre);
    }

    protected boolean estCoordonneeValide(Niveau grille, int x, int y) {
        return x < 0 || y < 0 || y >= grille.get_hauteur() || x >= grille.get_largeur();
    }
}
