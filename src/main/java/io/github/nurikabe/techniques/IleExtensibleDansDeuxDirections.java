package io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.ArrayList;
import java.util.List;

//classe représentant la technique qui teste si une ile est extensible uniquement dans 2 directions
public class IleExtensibleDansDeuxDirections extends Technique  {
    @Override
    protected String getIdentifiant() {
        return "basique_5";
    }

    /**
     * Méthode de parcours de la grille, elle vérifie qu'une ile est extensible dans éxactement 2 directions et si c'est le cas elle renvoie une liste avec la position de cette case
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
                if(grille.recupCase(x, y) instanceof CaseNombre)
                {
                    List<Coup> coups = new ArrayList<>();

                    //Test si les cases autour sont des cases blanches,
                    // il ne nous faut exactement 2 case blanche pour que la technique soit valide.
                    // Puisque les parallèles sont interdites, la 1ère et 3ème ligne, ou la 2ème et 4ème ligne seront exécutées,
                    // et indiquerons les coordonnées de la diagonale.
                    insertionCond(coups, grille, x - 1, y, this::estCaseBlanche);
                    insertionCond(coups, grille, x + 1, y, this::estCaseBlanche);
                    insertionCond(coups, grille, x, y - 1, this::estCaseBlanche);
                    insertionCond(coups, grille, x, y + 1, this::estCaseBlanche);

                    if (coups.size() == 2) {
                        //On évite le cas où les 2 cases blanches sont parallèles,
                        // puisque la technique voudra mettre une case en diagonale
                        if (coups.get(0).recupX() == coups.get(1).recupX() || coups.get(0).recupY() == coups.get(1).recupY())
                            continue;

                        if (!estCaseNoire(grille, coups.get(0).recupX(), coups.get(1).recupY())) {
                            return new PositionTechniques(this, List.of(new Coup(coups.get(0).recupX(), coups.get(1).recupY())));
                        }
                    }
                }
            }
        
        }   
        return null;
    }
}
