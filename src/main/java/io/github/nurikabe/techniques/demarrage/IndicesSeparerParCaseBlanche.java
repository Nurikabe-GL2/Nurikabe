package io.github.nurikabe.techniques.demarrage;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la technique de jeux pour 2 cases de nombres séparées par une case blanche
 */
public class IndicesSeparerParCaseBlanche extends Technique {
    @Override
    protected String getIdentifiant() {
        return "demarrage_2";
    }

    /**
     * Méthode de parcours de la grille, elle teste toute les cases et vérifie que la technique est applicable, si oui elle ajoute la case sur laquelle appliqué la technique à la liste
     *
     * @param grille la grille en question
     *
     * @return une Position technique ou null
     */
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