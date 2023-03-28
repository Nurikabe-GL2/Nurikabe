package io.github.nurikabe.techniques.demarrage;

import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe public représentant les différentes techniques
 */
public class IleDeUn extends Technique {
    @Override
    protected String getIdentifiant() {
        return "demarrage_1";
    }

    /**
     * La techniques recherche les cases de nombres avec le chiffre un,
     * Et retourne la position du dit un
     *
     * @return la position du 1
     */
    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int x = 0; x < grille.recupLargeur(); x++) {
            for (int y = 0; y < grille.getHauteur(); y++) {
                if (grille.etatCase(x, y).equals("1")) {
                    List<Cible> cibles = new ArrayList<>();

                    insertionCond(cibles, grille, x - 1, y, this::estCaseBlanche, "n");
                    insertionCond(cibles, grille, x + 1, y, this::estCaseBlanche, "n");
                    insertionCond(cibles, grille, x, y - 1, this::estCaseBlanche, "n");
                    insertionCond(cibles, grille, x, y + 1, this::estCaseBlanche, "n");

                    if (!cibles.isEmpty())
                        return new PositionTechniques(this, cibles);
                }
            }
        }
        return null;
    }
}
