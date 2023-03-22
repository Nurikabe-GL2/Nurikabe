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
        for(int i=0;i<grille.get_hauteur();i++)
        {
            for(int j=0;j<grille.get_largeur();j++)
            {
                int cpt = 0;
                if(grille.get_case(i, j) instanceof CaseNombre)
                {
                    List<Coup> liste = new ArrayList<>();
                    
		            //test si la case de gauche de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,i-1,j)&& grille.get_case(i - 1, j).get_cont_case().equals("b"))
                        cpt++;
                    
                        //test si la case de droite de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,i+1,j)&& grille.get_case(i + 1, j).get_cont_case().equals("b"))
                        cpt++;
                    
                        //test si la case en bas de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,i,j-1)&& grille.get_case(i, j - 1).get_cont_case().equals("b"))
                        cpt++;

                    //test si la case en bas de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,i,j+1)&& grille.get_case(i, j + 1).get_cont_case().equals("b"))
                        cpt++;
                    
                    //si elle est extensible uniquement dans 2 directions alors la technique est valide
                    if(cpt==2)
                        liste.add(new Coup(i,j));   
                    		
					if(!liste.isEmpty())
                        			return new PositionTechniques(liste);
                }
            }
        
        }   
        return null;
    }
}
