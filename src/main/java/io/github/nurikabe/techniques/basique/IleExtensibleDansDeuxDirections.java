package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.cases.CaseNombre;
import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

import java.util.ArrayList;
import java.util.List;

/**
 * Dans certains cas, un îlot de 2 ou le dernier carré d'un îlot plus grand ne peut être agrandi que dans deux directions perpendiculaires.
 * Dans ce cas, quelle que soit la direction dans laquelle l’extension de l'îlot aura lieu, le carré diagonal doit faire partie d'un mur comme indiqué dans le diagramme de droite ci-dessous.
 */
public class IleExtensibleDansDeuxDirections extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_5";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int x = 0; x < grille.getLargeur(); x++) {
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
                        if (coups.get(0).x() == coups.get(1).x() || coups.get(0).y() == coups.get(1).y())
                            continue;

                        if (!estCaseNoire(grille, coups.get(0).x(), coups.get(1).y())) {
                            return new PositionTechniques(this, new Cible(coups.get(0).x(), coups.get(1).y(), "n"));
                        }
                    }
                }
            }

        }
        return null;
    }
}
