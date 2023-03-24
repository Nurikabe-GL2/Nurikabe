package io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNormale;
import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.ArrayList;
import java.util.List;

//classe représentant la technique qui dit qu'il faut joindre deux murs si une case blanche est entre eux
public class ContinuiteDunMur extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_3";
    }

    /**
     * Méthode de parcours de la grille, elle vérifie qu'une case blanche possède éxactement 2 murs autour d'elle (sans les diagonales) et si c'est le cas elle renvoie une liste avec la position de cette case
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
                if(grille.recupCase(x, y) instanceof CaseNormale)
                {
                    List<Coup> liste = new ArrayList<>();
                    
		            //test si la case de gauche de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,x-1,y)&& grille.recupCase(x - 1, y).recupContenuCase().equals("n"))
                        cpt++;
                    
                        //test si la case de droite de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,x+1,y)&& grille.recupCase(x + 1, y).recupContenuCase().equals("n"))
                        cpt++;
                    
                        //test si la case en bas de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,x,y-1)&& grille.recupCase(x, y - 1).recupContenuCase().equals("n"))
                        cpt++;

                    //test si la case en bas de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,x,y+1)&& grille.recupCase(x, y + 1).recupContenuCase().equals("n"))
                        cpt++;
                    
                    //si elle possède éxactement 2 murs comme voisins alors la technique est valide pour cette case
                    if(cpt==2)
                        liste.add(new Coup(x,y));
                    		
					if(!liste.isEmpty())
                        			return new PositionTechniques(this, liste);
                }
            }
        
        }   
        return null;
    }
}
