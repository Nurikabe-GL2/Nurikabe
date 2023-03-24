package io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la techniques de jeux pour 2 cases de nombe séparer par un case blanche
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
        for (int x = 0; x < grille.recupLargeur(); x++) {
            for (int y = 0; y < grille.get_hauteur(); y++) {
                if (grille.recupCase(x, y) instanceof CaseNombre) {
                    List<Coup> liste = new ArrayList<>();

                    if (estValideCaseNombreEspace(grille, x - 2, y) && estCaseBlanche(grille, x - 1, y))
                        liste.add(new Coup(x - 1, y));
                    if (estValideCaseNombreEspace(grille, x + 2, y) && estCaseBlanche(grille, x + 1, y))
                        liste.add(new Coup(x + 1, y));
                    if (estValideCaseNombreEspace(grille, x, y - 2) && estCaseBlanche(grille, x, y - 1))
                        liste.add(new Coup(x, y - 1));
                    if (estValideCaseNombreEspace(grille, x, y + 2) && estCaseBlanche(grille, x, y + 1))
                        liste.add(new Coup(x, y + 1));
                    if (!liste.isEmpty())
                        return new PositionTechniques(this, liste);
                }
            }
        }
        return null;
    }
}