package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.CaseNormale;
import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

import java.util.ArrayList;
import java.util.List;

//classe représentant la technique de continuité de l'ile, elle vérifie si une case vérifie la technique Éviter une zone de mur 2x2 et test si il y'a une case blanche entre cette dernière et une ile
public class ContinuiteDeLile extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_7";
    }

    /**
     * Méthode de parcours de la grille, elle vérifie si une case vérifie la technique Éviter une zone de mur 2x2 et test si il y'a une case blanche entre cette dernière et une ile
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
                    List<Cible> liste = new ArrayList<>();
                    //si la case courante vérifie la technique de la zone de mur
                    //alors on vérifie s'il n'y à pas une case blanche qui est entre elle et une ile
                    if(estCaseBlanche(grille, x, y) && seraUnCarre(grille, x, y))
                    {
                        //si la case de gauche est valide et que c'est une case blanche et que la case de sa gauche est une ile
                        if (estCaseBlanche(grille, x - 1, y) && estCaseNombre(grille, x - 2, y))
                            liste.add(new Cible(x, y, "."));

                            //si la case de droite est valide et que c'est une case blanche et que la case de sa droite est une ile
                        else if (estCaseBlanche(grille, x + 1, y) && estCaseNombre(grille, x + 2, y))
                            liste.add(new Cible(x, y, "."));

                            //si la case en haut est valide et que c'est une case blanche et que la case en haut de cette dernière est une ile
                        else if (estCaseBlanche(grille, x, y - 1) && estCaseNombre(grille, x, y - 2) )
                            liste.add(new Cible(x, y, "."));

                            //si la case en bas est valide et que c'est une case blanche et que la case en bas de cette dernière est une ile
                        else if (estCaseBlanche(grille, x, y + 1) && estCaseNombre(grille, x, y + 2))
                            liste.add(new Cible(x, y, "."));
					
                        if(!liste.isEmpty())
                        	return new PositionTechniques(this, liste);
                    }
                }
        
            }
        }   
        return null;
    }
}