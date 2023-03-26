package io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNormale;
import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la technique du nurikabe, cette dernière implémente une méthode de la technique du même nom qui vérifie si une case blanche à 3 mur autour d'elle
 */
public class EviterZoneMur extends Technique  {
    @Override
    protected String getIdentifiant() {
        return "basique_9";
    }

    /**
     * Méthode de parcours de la grille, elle vérifie qu'une case blanche à au moins 3 murs autour d'elle et si c'est le cas elle renvoie une liste avec la position de cette case
     * @param grille la grille en question
     * @return une Position technique ou null
     */
    @Override
    public PositionTechniques tester(Niveau grille)
    {
        for(int x=0;x<grille.recupLargeur();x++)
        {
            for(int y=0;y<grille.get_hauteur();y++)
            {
                if(estCaseBlanche(grille, x, y) && seraUnCarre(grille, x, y))
                {
                    return new PositionTechniques(this, List.of(new Coup(x, y)));
                }
            }
        
        }   
        return null;
    }
}