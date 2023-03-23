package io.github.nurikabe.techniques;

import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.ArrayList;
import java.util.List;
/**
 * Classe public représentant les différentes techniques
 */
public class IleDeUn extends Technique {
    @Override
    protected String getIdentifiant() {
        return "demarrage_1";
    }

    /**
     * La techniques recherche les cases de nombres avec le chiffre un,
     * Et retourne la position du dit un
     * @return la position du 1
     */
    @Override
    public PositionTechniques tester(Niveau grille)
    {
        
        for(int x=0;x<grille.get_largeur();x++)
        {
            for(int y=0;y<grille.get_hauteur();y++)
            {
                if(grille.etat_case(x, y).equals("1"))
                {
                    List<Coup> liste = new ArrayList<>();

                    if(estValide(grille,x-1,y))
                        liste.add(new Coup(x-1, y));
                    if(estValide(grille,x+1,y))
                        liste.add(new Coup(x+1, y));
                    if(estValide(grille,x,y-1))
                        liste.add(new Coup(x, y-1));
                    if(estValide(grille,x,y+1))
                        liste.add(new Coup(x, y+1));
                    if(!liste.isEmpty())
                        return new PositionTechniques(this, liste);
                }
            }   
        }
        return null;
    }
}
