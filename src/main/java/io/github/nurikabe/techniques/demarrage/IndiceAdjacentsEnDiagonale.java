package io.github.nurikabe.techniques.demarrage;

import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

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
                    final List<Cible> coups = new ArrayList<>();

                    //Verifier la presence d'une case diagonale
                    if (estUnNombre(grille, x + 1, y + 1)) {
                        insertionCond(coups, grille, x, y + 1, this::estCaseBlanche, "n");
                        insertionCond(coups, grille, x + 1, y, this::estCaseBlanche, "n");
                    } else if (estUnNombre(grille, x + 1, y - 1)) {
                        insertionCond(coups, grille, x, y - 1, this::estCaseBlanche, "n");
                        insertionCond(coups, grille, x + 1, y, this::estCaseBlanche, "n");
                    } else if (estUnNombre(grille, x - 1, y + 1)) {
                        insertionCond(coups, grille, x, y + 1, this::estCaseBlanche, "n");
                        insertionCond(coups, grille, x - 1, y, this::estCaseBlanche, "n");
                    } else if (estUnNombre(grille, x - 1, y - 1)) {
                        insertionCond(coups, grille, x - 1, y, this::estCaseBlanche, "n");
                        insertionCond(coups, grille, x, y - 1, this::estCaseBlanche, "n");
                    }

                    if (!coups.isEmpty())
                        return new PositionTechniques(this, coups);
                }
            }
        }

        return null;
    }
}