package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.CaseNormale;
import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

import java.util.ArrayList;
import java.util.List;

/**
 * Après avoir résolu les premières étapes en utilisant les techniques de départ décrites ci-dessus,
 * le puzzle du diagramme de gauche ci-dessous comporte deux carrés ? en rouge.
 * Comme ces carrés sont entourés de murs horizontalement et verticalement,
 * ils ne peuvent pas appartenir à une île et doivent donc être ombrés pour faire partie du mur.
 */
public class CarreIsole extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_1";
    }

    @Override
    public PositionTechniques tester(Niveau grille) {
        for (int i = 0; i < grille.getLargeur(); i++) {
            for (int j = 0; j < grille.getHauteur(); j++) {
                if (grille.recupCase(i, j) instanceof CaseNormale && grille.recupCase(i, j).getContenuCase().equals("b")) {
                    List<Cible> liste = new ArrayList<>();

                    //test si la case de gauche de la case courante est valide ou que c'est une case noire
                    if (estCaseNoire(grille, i - 1, j)) {
                        //test si la case de droite de la case courante est valide ou que c'est une case noire
                        if (estCaseNoire(grille, i + 1, j)) {
                            //test si la case en bas de la case courante est valide ou que c'est une case noire
                            if (estCaseNoire(grille, i, j - 1)) {
                                //test si la case en bas de la case courante est valide ou que c'est une case noire
                                if (estCaseNoire(grille, i, j + 1))
                                    liste.add(new Cible(i, j, "n"));
                            }

                            if (!liste.isEmpty())
                                return new PositionTechniques(this, liste);
                        }
                    }
                }
            }
        }
        return null;
    }
}