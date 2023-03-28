package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

import java.util.ArrayList;
import java.util.List;

//classe représentant la technique qui teste si une ile est extensible uniquement dans 2 directions
public class IleExtensibleDansDeuxDirections extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_5";
    }

    /**
     * Méthode de parcours de la grille, elle vérifie qu'une ile est extensible dans éxactement 2 directions et si c'est le cas elle renvoie une liste avec la position de cette case
     *
     * @param grille la grille en question
     *
     * @return une Position technique ou null
     */
    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int x = 0; x < grille.recupLargeur(); x++) {
            for (int y = 0; y < grille.getHauteur(); y++) {
                if (grille.recupCase(x, y) instanceof CaseNombre) {
                    List<Cible> coups = new ArrayList<>();

                    //Test si les cases autour sont des cases blanches,
                    // il ne nous faut exactement 2 case blanche pour que la technique soit valide.
                    // Puisque les parallèles sont interdites, la 1ère et 3ème ligne, ou la 2ème et 4ème ligne seront exécutées,
                    // et indiquerons les coordonnées de la diagonale.
                    insertionCond(coups, grille, x - 1, y, this::estCaseBlanche, "n");
                    insertionCond(coups, grille, x + 1, y, this::estCaseBlanche, "n");
                    insertionCond(coups, grille, x, y - 1, this::estCaseBlanche, "n");
                    insertionCond(coups, grille, x, y + 1, this::estCaseBlanche, "n");

                    if (coups.size() == 2) {
                        //On évite le cas où les 2 cases blanches sont parallèles,
                        // puisque la technique voudra mettre une case en diagonale
                        if (coups.get(0).getX() == coups.get(1).getX() || coups.get(0).getY() == coups.get(1).getY())
                            continue;

                        if (!estCaseNoire(grille, coups.get(0).getX(), coups.get(1).getY())) {
                            return new PositionTechniques(this, new Cible(coups.get(0).getX(), coups.get(1).getY(), "n"));
                        }
                    }
                }
            }

        }
        return null;
    }
}
