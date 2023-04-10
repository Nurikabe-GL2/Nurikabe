package io.github.nurikabe.techniques.demarrage;

import io.github.nurikabe.cases.Case;
import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

import java.util.ArrayList;
import java.util.List;

/**
 * Comme il s'agit d'une île avec un seul carré, nous pouvons poser des murs adjacents et horizontaux.
 */
public class IleDeUn extends Technique {
    @Override
    protected String getIdentifiant() {
        return "demarrage_1";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int x = 0; x < grille.getLargeur(); x++) {
            for (int y = 0; y < grille.getHauteur(); y++) {
                if (grille.etatCase(x, y).equals("1")) {
                    List<Cible> cibles = new ArrayList<>();

                    insertionCond(cibles, grille, x - 1, y, this::estCaseBlanche, Case.Type.NOIR);
                    insertionCond(cibles, grille, x + 1, y, this::estCaseBlanche, Case.Type.NOIR);
                    insertionCond(cibles, grille, x, y - 1, this::estCaseBlanche, Case.Type.NOIR);
                    insertionCond(cibles, grille, x, y + 1, this::estCaseBlanche, Case.Type.NOIR);

                    if (!cibles.isEmpty())
                        return new PositionTechniques(this, cibles);
                }
            }
        }
        return null;
    }
}
