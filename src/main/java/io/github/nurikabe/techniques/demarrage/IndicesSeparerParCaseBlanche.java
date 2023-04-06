package io.github.nurikabe.techniques.demarrage;

import io.github.nurikabe.cases.CaseNombre;
import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

import java.util.ArrayList;
import java.util.List;

/**
 * Tous les indices doivent être séparés les uns des autres par des murs.
 * Par conséquent, lorsque deux indices se trouvent sur la même ligne ou colonne et sont séparés par une case,
 * la case intermédiaire doit être un mur.
 */
public class IndicesSeparerParCaseBlanche extends Technique {
    @Override
    protected String getIdentifiant() {
        return "demarrage_2";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int x = 0; x < grille.getLargeur(); x++) {
            for (int y = 0; y < grille.getHauteur(); y++) {
                if (grille.recupCase(x, y) instanceof CaseNombre) {
                    List<Cible> liste = new ArrayList<>();

                    if (estCaseNombre(grille, x - 2, y) && estCaseBlanche(grille, x - 1, y))
                        liste.add(new Cible(x - 1, y, "n"));
                    if (estCaseNombre(grille, x + 2, y) && estCaseBlanche(grille, x + 1, y))
                        liste.add(new Cible(x + 1, y, "n"));
                    if (estCaseNombre(grille, x, y - 2) && estCaseBlanche(grille, x, y - 1))
                        liste.add(new Cible(x, y - 1, "n"));
                    if (estCaseNombre(grille, x, y + 2) && estCaseBlanche(grille, x, y + 1))
                        liste.add(new Cible(x, y + 1, "n"));
                    if (!liste.isEmpty())
                        return new PositionTechniques(this, liste);
                }
            }
        }
        return null;
    }
}