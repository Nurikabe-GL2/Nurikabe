package io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.CaseNormale;
import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.ArrayList;
import java.util.List;

//classe représentant la technique de continuité de l'ile, elle vérifie si une case vérifie la technique Éviter une zone de mur 2x2 et test si il y'a une case blanche entre cette dernière et une ile
public class ContinuiteDeLile extends Technique{
    /**
     * Méthode de parcours de la grille, elle vérifie si une case vérifie la technique Éviter une zone de mur 2x2 et test si il y'a une case blanche entre cette dernière et une ile
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
                    //si la case courante vérifie la technique de la zone de mur
                    //alors on vérifie s'il n'y à pas une case blanche qui est entre elle et une ile
                    if(new EviterZoneMur().tester(grille)!=null)
                    {
                        //si la case de gauche est valide et que c'est une case blanche et que la case de sa gauche est une ile
                        if((estCoordonneeValide(grille,i-1,j)&& grille.get_case(i - 1, j).get_cont_case().equals("b"))&&(estCoordonneeValide(grille,i-2,j)&&grille.get_case(i - 2, j) instanceof CaseNombre))
                            liste.add(new Coup(i,j));
                    	
                        //si la case de droite est valide et que c'est une case blanche et que la case de sa droite est une ile
                        else if((estCoordonneeValide(grille,i+1,j)&& grille.get_case(i + 1, j).get_cont_case().equals("b"))&&(estCoordonneeValide(grille,i+2,j)&&grille.get_case(i + 2, j) instanceof CaseNombre))
                            liste.add(new Coup(i,j));

                        //si la case en haut est valide et que c'est une case blanche et que la case en haut de cette dernière est une ile
                        else if((estCoordonneeValide(grille,i,j-1)&& grille.get_case(i, j - 1).get_cont_case().equals("b"))&&(estCoordonneeValide(grille,i,j-2)&&grille.get_case(i, j - 2) instanceof CaseNombre))
                            liste.add(new Coup(i,j));

                        //si la case en bas est valide et que c'est une case blanche et que la case en bas de cette dernière est une ile
                        else if((estCoordonneeValide(grille,i,j+1)&& grille.get_case(i, j + 1).get_cont_case().equals("b"))&&(estCoordonneeValide(grille,i,j+2)&&grille.get_case(i, j + 2) instanceof CaseNombre))
                            liste.add(new Coup(i,j));
					
                        if(!liste.isEmpty())
                        	return new PositionTechniques(liste);
                    }
                }
        
            }
        }   
        return null;
    }
}