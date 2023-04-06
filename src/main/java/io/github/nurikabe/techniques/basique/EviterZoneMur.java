package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

/**
 * Selon les règles, il n'est pas permis d'avoir des murs de 2x2 ou plus.
 * Par conséquent, le carré surligné ci-dessous ne peut pas faire partie du mur et est marqué d'un point indiquant qu'il appartient à l'une des îles.
 */
public class EviterZoneMur extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_9";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int x = 0; x < grille.getLargeur(); x++) {
            for (int y = 0; y < grille.getHauteur(); y++) {
                if (estCaseBlanche(grille, x, y) && seraUnCarre(grille, x, y)) {
                    return new PositionTechniques(this, new Cible(x, y, "n"));
                }
            }

        }
        return null;
    }
}