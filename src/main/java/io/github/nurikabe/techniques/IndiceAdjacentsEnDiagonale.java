package io.github.nurikabe.techniques;

import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.List;

public class IndiceAdjacentsEnDiagonale extends Technique {
    @Override
    protected String getIdentifiant() {
        return "demarrage_3";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int y = 0; y < grille.get_largeur(); y++) {
            for (int x = 0; x < grille.get_hauteur(); x++) {
                if (estUnNombre(grille, x, y)) {
                    //Verifier la presence d'une case diagonale
                    if (estUnNombre(grille, x + 1, y + 1)) {
                        return new PositionTechniques(List.of(
                                new Coup(x, y + 1),
                                new Coup(x + 1, y)
                        ));
                    } else if (estUnNombre(grille, x + 1, y - 1)) {
                        return new PositionTechniques(List.of(
                                new Coup(x, y - 1),
                                new Coup(x + 1, y)
                        ));
                    } else if (estUnNombre(grille, x - 1, y + 1)) {
                        return new PositionTechniques(List.of(
                                new Coup(x, y + 1),
                                new Coup(x - 1, y)
                        ));
                    } else if (estUnNombre(grille, x - 1, y - 1)) {
                        return new PositionTechniques(List.of(
                                new Coup(x - 1, y),
                                new Coup(x, y - 1)
                        ));
                    }
                }
            }
        }

        return null;
    }
}