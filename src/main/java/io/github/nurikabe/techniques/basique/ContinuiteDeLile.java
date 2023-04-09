package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.cases.Case;
import io.github.nurikabe.cases.CaseNormale;
import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

import java.util.ArrayList;
import java.util.List;

/**
 * La case marquée d'un point rouge dans le schéma de gauche ci-dessous doit faire partie d'un îlot pour éviter une surface de mur de 2x2.
 * Cela signifie que la case située à sa gauche doit également faire partie de la même île et est donc également marquée d'un point.
 * L'île de 3 est maintenant terminée et nous pouvons l'entourer d'un mur.
 */
public class ContinuiteDeLile extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_7";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int x = 0; x < grille.getLargeur(); x++) {
            for (int y = 0; y < grille.getHauteur(); y++) {
                int cpt = 0;
                if (grille.recupCase(x, y) instanceof CaseNormale) {
                    List<Cible> liste = new ArrayList<>();
                    //si la case courante vérifie la technique de la zone de mur
                    //alors on vérifie s'il n'y à pas une case blanche qui est entre elle et une ile
                    if (estCaseBlanche(grille, x, y) && seraUnCarre(grille, x, y)) {
                        //si la case de gauche est valide et que c'est une case blanche et que la case de sa gauche est une ile
                        if (estCaseBlanche(grille, x - 1, y) && estCaseNombre(grille, x - 2, y))
                            liste.add(new Cible(x, y, Case.Type.POINT));

                            //si la case de droite est valide et que c'est une case blanche et que la case de sa droite est une ile
                        else if (estCaseBlanche(grille, x + 1, y) && estCaseNombre(grille, x + 2, y))
                            liste.add(new Cible(x, y, Case.Type.POINT));

                            //si la case en haut est valide et que c'est une case blanche et que la case en haut de cette dernière est une ile
                        else if (estCaseBlanche(grille, x, y - 1) && estCaseNombre(grille, x, y - 2))
                            liste.add(new Cible(x, y, Case.Type.POINT));

                            //si la case en bas est valide et que c'est une case blanche et que la case en bas de cette dernière est une ile
                        else if (estCaseBlanche(grille, x, y + 1) && estCaseNombre(grille, x, y + 2))
                            liste.add(new Cible(x, y, Case.Type.POINT));

                        if (!liste.isEmpty())
                            return new PositionTechniques(this, liste);
                    }
                }

            }
        }
        return null;
    }
}