package io.github.nurikabe.techniques;

import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.ArrayList;
import java.util.List;

public class IndiceAdjacentsEnDiagonale extends Technique {
    @Override
    protected String getIdentifiant() {
        return "demarrage_3";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int y = 0; y < grille.recupLargeur(); y++) {
            for (int x = 0; x < grille.get_hauteur(); x++) {
                if (estUnNombre(grille, x, y)) {
                    final List<Coup> coups = new ArrayList<>();

                    //Verifier la presence d'une case diagonale
                    if (estUnNombre(grille, x + 1, y + 1)) {
                        insertionCond(coups, grille, x, y + 1, this::estCaseBlanche);
                        insertionCond(coups, grille, x + 1, y, this::estCaseBlanche);
                    } else if (estUnNombre(grille, x + 1, y - 1)) {
                        insertionCond(coups, grille, x, y - 1, this::estCaseBlanche);
                        insertionCond(coups, grille, x + 1, y, this::estCaseBlanche);
                    } else if (estUnNombre(grille, x - 1, y + 1)) {
                        insertionCond(coups, grille, x, y + 1, this::estCaseBlanche);
                        insertionCond(coups, grille, x - 1, y, this::estCaseBlanche);
                    } else if (estUnNombre(grille, x - 1, y - 1)) {
                        insertionCond(coups, grille, x - 1, y, this::estCaseBlanche);
                        insertionCond(coups, grille, x, y - 1, this::estCaseBlanche);
                    }

                    if (!coups.isEmpty())
                        return new PositionTechniques(this, coups);
                }
            }
        }

        return null;
    }
}