package io.github.nurikabe.techniques.basique;

import io.github.nurikabe.CaseNormale;
import io.github.nurikabe.Niveau;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la techniques de jeux pour une case Blanche isolé
 */
public class CarreIsole extends Technique {
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
        
        for(int i=0;i<grille.recupLargeur();i++)
        {
            for(int j=0;j<grille.get_hauteur();j++)
            {
                if(grille.recupCase(i, j) instanceof CaseNormale && grille.recupCase(i, j).recupContenuCase().equals("b"))
                {
                    List<Cible> liste = new ArrayList<>();

		//test si la case de gauche de la case courante est valide ou que c'est une case noir
                    if(estCoordonneeValide(grille,i-1,j) && grille.recupCase(i - 1, j).recupContenuCase().equals("n"))
                    {
                    	//test si la case de droite de la case courante est valide ou que c'est une case noir
                    	if(estCoordonneeValide(grille,i+1,j) && grille.recupCase(i + 1, j).recupContenuCase().equals("n"))
                    	{
                    		//test si la case en bas de la case courante est valide ou que c'est une case noir
                    		if(estCoordonneeValide(grille,i,j-1) && grille.recupCase(i, j - 1).recupContenuCase().equals("n"))
                    		{
                    			//test si la case en bas de la case courante est valide ou que c'est une case noir
                    			if(estCoordonneeValide(grille,i,j+1) && grille.recupCase(i, j + 1).recupContenuCase().equals("n"))
                    				liste.add(new Cible(i,j, "n"));
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