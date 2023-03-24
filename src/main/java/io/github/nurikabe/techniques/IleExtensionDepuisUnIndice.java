package io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.ArrayList;
import java.util.List;

//classe représentant la technique qui teste si une ile est extensible uniquement dans 1 direction
public class IleExtensionDepuisUnIndice extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_4";
    }

    /**
     * Méthode de parcours de la grille, elle vérifie qu'une ile est extensible dans éxactement 1 direction et si c'est le cas elle renvoie une liste avec la position de cette case
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
                int cpt = 0;
                if(grille.recupCase(x, y) instanceof CaseNombre)
                {
                    List<Coup> liste = new ArrayList<>();
                    
		            //test si la case de gauche de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,x-1,y)&& grille.recupCase(x - 1, y).recupContenuCase().equals("b"))
                        cpt++;
                    
                        //test si la case de droite de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,x+1,y)&& grille.recupCase(x + 1, y).recupContenuCase().equals("b"))
                        cpt++;
                    
                        //test si la case en bas de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,x,y-1)&& grille.recupCase(x, y - 1).recupContenuCase().equals("b"))
                        cpt++;

                    //test si la case en bas de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,x,y+1)&& grille.recupCase(x, y + 1).recupContenuCase().equals("b"))
                        cpt++;
                    
                    //si elle est extensible uniquement dans 2 directions alors la technique est valide
                    if(cpt==1)
                        liste.add(new Coup(x,y));
                    		
					if(!liste.isEmpty())
                        			return new PositionTechniques(this, liste);
                }
            }
        
        }   
        return null;
    }
}
