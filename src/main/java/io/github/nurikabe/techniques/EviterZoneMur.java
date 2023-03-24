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
                int cpt = 0;
                if(grille.recupCase(x, y) instanceof CaseNormale && grille.recupCase(x, y).recupContenuCase().equals("b"))
                {
                    List<Coup> liste = new ArrayList<>();
                    
		            //test si la case de gauche de la case courante est valide et que c'est une case noir
                    if(estCoordonneeValide(grille,x-1,y)&& grille.recupCase(x - 1, y).recupContenuCase().equals("n"))
                        cpt++;
                    
                        //test si la case de droite de la case courante est valide et que c'est une case noir
                    if(estCoordonneeValide(grille,x+1,y)&& grille.recupCase(x + 1, y).recupContenuCase().equals("n"))
                        cpt++;
                    
                        //test si la case en bas de la case courante est valide et que c'est une case noir
                    if(estCoordonneeValide(grille,x,y-1)&& grille.recupCase(x, y - 1).recupContenuCase().equals("n"))
                        cpt++;

                    //test si la case en bas de la case courante est valide et que c'est une case noir
                    if(estCoordonneeValide(grille,x,y+1)&& grille.recupCase(x, y + 1).recupContenuCase().equals("n"))
                        cpt++;
                    
                    //test si la case en haut à gauche de la case courante est valide et que c'est une case noir
                    if(estCoordonneeValide(grille,x-1,y-1)&& grille.recupCase(x - 1, y - 1).recupContenuCase().equals("n"))
                        cpt++;
                    
                        //test si la case en haut à droite de la case courante est valide et que c'est une case noir
                    if(estCoordonneeValide(grille,x+1,y-1)&& grille.recupCase(x + 1, y - 1).recupContenuCase().equals("n"))
                        cpt++;
                    
                        //test si la case en bas à gauche de la case courante est valide et que c'est une case noir
                    if(estCoordonneeValide(grille,x-1,y+1)&& grille.recupCase(x - 1, y + 1).recupContenuCase().equals("n"))
                        cpt++;
                        
                    //test si la case en bas à droite de la case courante est valide et que c'est une case noir
                    if(estCoordonneeValide(grille,x+1,y+1)&& grille.recupCase(x + 1, y + 1).recupContenuCase().equals("n"))
                        cpt++;
                
                    if(cpt>=3)
                        liste.add(new Coup(x,y));
                    		
					if(!liste.isEmpty())
                        			return new PositionTechniques(this, liste);
                }
            }
        
        }   
        return null;
    }
}