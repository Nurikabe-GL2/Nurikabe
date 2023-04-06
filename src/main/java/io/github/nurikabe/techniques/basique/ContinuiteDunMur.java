package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

/**
 * Dans le puzzle ci-dessous, si le carré ? en rouge fait partie d'une île, le mur du haut sera cloisonné.
 * Par conséquent, pour maintenir la continuité du mur selon les règles du Nurikabe, ce carré doit faire partie d'un mur.
 */
public class ContinuiteDunMur extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_3";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int x = 0; x < grille.getLargeur(); x++) {
            for (int y = 0; y < grille.getHauteur(); y++) {
                if (grille.recupCase(x, y).recupContenuCase().equals("b")) {
                    int cpt = 0;

                    //test si la case de gauche de la case courante est valide et que c'est une case noire
                    if (estCaseNoire(grille, x - 1, y))
                        cpt++;

                    //test si la case de droite de la case courante est valide et que c'est une case noire
                    if (estCaseNoire(grille, x + 1, y))
                        cpt++;

                    //test si la case en bas de la case courante est valide et que c'est une case noire
                    if (estCaseNoire(grille, x, y - 1))
                        cpt++;

                    //test si la case en bas de la case courante est valide et que c'est une case noire
                    if (estCaseNoire(grille, x, y + 1))
                        cpt++;

                    //si elle possède exactement 2 murs comme voisins et que cela ne formera pas un carré,
                    // alors la technique est valide pour cette case
                    if (cpt >= 2 && !seraUnCarre(grille, x, y)) {
                        return new PositionTechniques(this, new Cible(x, y, "n"));
                    }
                }
            }

        }
        return null;
    }
}
