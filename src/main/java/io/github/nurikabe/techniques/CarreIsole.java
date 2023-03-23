package io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNormale;
import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la techniques de jeux pour une case Blanche isolé
 */
public class CarreIsole extends Technique  {
    @Override
    protected String getIdentifiant() {
        return "basique_1";
    }

    /**
     * Méthode de parcours de la grille, elle teste toute les cases et vérifie que la technique est applicable, si oui elle ajoute la case sur laquelle appliqué la technique à la liste
     * @param grille la grille en question
     * @return une Position technique ou null
     */
    @Override
    public PositionTechniques tester(Niveau grille)
    {
        
        for(int i=0;i<grille.get_largeur();i++)
        {
            for(int j=0;j<grille.get_hauteur();j++)
            {
                if(grille.get_case(i, j) instanceof CaseNormale && grille.get_case(i, j).get_cont_case().equals("b"))
                {
                    List<Coup> liste = new ArrayList<>();

		//test si la case de gauche de la case courante est valide ou que c'est une case noir
                    if(estCoordonneeValide(grille,i-1,j) && grille.get_case(i - 1, j).get_cont_case().equals("n"))
                    {
                    	//test si la case de droite de la case courante est valide ou que c'est une case noir
                    	if(estCoordonneeValide(grille,i+1,j) && grille.get_case(i + 1, j).get_cont_case().equals("n"))
                    	{
                    		//test si la case en bas de la case courante est valide ou que c'est une case noir
                    		if(estCoordonneeValide(grille,i,j-1) && grille.get_case(i, j - 1).get_cont_case().equals("n"))
                    		{
                    			//test si la case en bas de la case courante est valide ou que c'est une case noir
                    			if(estCoordonneeValide(grille,i,j+1) && grille.get_case(i, j + 1).get_cont_case().equals("n"))
                    				liste.add(new Coup(i,j));
                    		}
                    		
					if(!liste.isEmpty())
                        			return new PositionTechniques(this, liste);
                    	}
                    }
                }
            }   
        }
        return null;
    }
}