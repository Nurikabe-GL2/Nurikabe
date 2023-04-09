package io.github.nurikabe.techniques.demarrage;

import io.github.nurikabe.cases.Case;
import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

import java.util.ArrayList;
import java.util.List;

/**
 * Similaire à la technique précédente, quand deux indices sont adjacents en diagonales,
 * il faut mettre des murs à toutes les cases touchants les deux indices.
 */
public class IndiceAdjacentsEnDiagonale extends Technique {
    @Override
    protected String getIdentifiant() {
        return "demarrage_3";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int y = 0; y < grille.getLargeur(); y++) {
            for (int x = 0; x < grille.getHauteur(); x++) {
                if (estUnNombre(grille, x, y)) {
                    final List<Cible> coups = new ArrayList<>();

                    //Verifier la presence d'une case diagonale
                    if (estUnNombre(grille, x + 1, y + 1)) {
                        insertionCond(coups, grille, x, y + 1, this::estCaseBlanche, Case.Type.NOIR);
                        insertionCond(coups, grille, x + 1, y, this::estCaseBlanche, Case.Type.NOIR);
                    } else if (estUnNombre(grille, x + 1, y - 1)) {
                        insertionCond(coups, grille, x, y - 1, this::estCaseBlanche, Case.Type.NOIR);
                        insertionCond(coups, grille, x + 1, y, this::estCaseBlanche, Case.Type.NOIR);
                    } else if (estUnNombre(grille, x - 1, y + 1)) {
                        insertionCond(coups, grille, x, y + 1, this::estCaseBlanche, Case.Type.NOIR);
                        insertionCond(coups, grille, x - 1, y, this::estCaseBlanche, Case.Type.NOIR);
                    } else if (estUnNombre(grille, x - 1, y - 1)) {
                        insertionCond(coups, grille, x - 1, y, this::estCaseBlanche, Case.Type.NOIR);
                        insertionCond(coups, grille, x, y - 1, this::estCaseBlanche, Case.Type.NOIR);
                    }

                    if (!coups.isEmpty())
                        return new PositionTechniques(this, coups);
                }
            }
        }

        return null;
    }
}