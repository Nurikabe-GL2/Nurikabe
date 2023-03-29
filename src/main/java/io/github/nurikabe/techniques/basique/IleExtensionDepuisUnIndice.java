package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

import java.util.ArrayList;
import java.util.List;

//classe représentant la technique qui teste si une ile est extensible uniquement dans 1 direction
public class IleExtensionDepuisUnIndice extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_4";
    }

    /**
     * Méthode de parcours de la grille, elle vérifie qu'une ile est extensible dans éxactement 1 direction et si c'est le cas elle renvoie une liste avec la position de cette case
     *
     * @param grille la grille en question
     *
     * @return une Position technique ou null
     */
    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int x = 0; x < grille.getLargeur(); x++) {
            for (int y = 0; y < grille.getHauteur(); y++) {
                if (grille.recupCase(x, y) instanceof CaseNombre) {
                    List<Cible> coups = new ArrayList<>();

                    //Test si les cases autour sont des cases blanches,
                    // il ne nous faut exactement 1 case blanche pour que la technique soit valide
                    insertionCond(coups, grille, x - 1, y, this::estCaseBlanche, ".");
                    insertionCond(coups, grille, x + 1, y, this::estCaseBlanche, ".");
                    insertionCond(coups, grille, x, y - 1, this::estCaseBlanche, ".");
                    insertionCond(coups, grille, x, y + 1, this::estCaseBlanche, ".");

                    if (coups.size() == 1) {
                        return new PositionTechniques(this, coups);
                    }
                }
            }
        }

        return null;
    }
}
