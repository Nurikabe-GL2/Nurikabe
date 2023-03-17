package io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNormale;
import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.ArrayList;
import java.util.List;

//classe représentant la technique qui dit qu'il faut joindre deux murs si une case blanche est entre eux
public class ContinuiteDunMur implements Technique {
    /**
     * Méthode de parcours de la grille, elle vérifie qu'une case blanche possède éxactement 2 murs autour d'elle (sans les diagonales) et si c'est le cas elle renvoie une liste avec la position de cette case
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
                if(grille.get_case(i, j) instanceof CaseNormale)
                {
                    List<Coup> liste = new ArrayList<>();
                    
		            //test si la case de gauche de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,i-1,j)&& grille.get_case(i - 1, j).get_cont_case().equals("n"))
                        cpt++;
                    
                        //test si la case de droite de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,i+1,j)&& grille.get_case(i + 1, j).get_cont_case().equals("n"))
                        cpt++;
                    
                        //test si la case en bas de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,i,j-1)&& grille.get_case(i, j - 1).get_cont_case().equals("n"))
                        cpt++;

                    //test si la case en bas de la case courante est valide et que c'est une case blanche
                    if(estCoordonneeValide(grille,i,j+1)&& grille.get_case(i, j + 1).get_cont_case().equals("n"))
                        cpt++;
                    
                    //si elle possède éxactement 2 murs comme voisins alors la technique est valide pour cette case
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
