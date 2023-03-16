package main.java.io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNormal;

/**
 * Classe représentant la techniques de jeux pour une case Blanche isolé
 */
public class CarreIsole implements Technique  {
    
    /**
     * Méthode de parcours de la grille, elle teste toute les cases et vérifie que la technique est applicable, si oui elle ajoute la case sur laquelle appliqué la technique à la liste
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
                if(grille.get(i).get(j).get_case() instanceof CaseNormal && grille.get(i).get(j).get_case().get_etat()=="b")
                {
                    List<Coup> liste = new ArrayList<>();

		//test si la case de gauche de la case courante est valide ou que c'est une case noir
                    if(estCoordonneeValide(grille,i-1,j)||grille.get(i-1).get(j).get_case().get_etat()=="n")
                    {
                    	//test si la case de droite de la case courante est valide ou que c'est une case noir
                    	if(estCoordonneeValide(grille,i+1,j)||grille.get(i+1).get(j).get_case().get_etat()=="n")
                    	{
                    		//test si la case en bas de la case courante est valide ou que c'est une case noir
                    		if(estCoordonneeValide(grille,i,j-1)||grille.get(i).get(j-1).get_case().get_etat()=="n")
                    		{
                    			//test si la case en bas de la case courante est valide ou que c'est une case noir
                    			if(estCoordonneeValide(grille,i,j+1)||grille.get(i).get(j+1).get_case().get_etat()=="n")
                    				liste.add(new Coup(i,j));
                    		}
                    		
					if(!liste.isEmpty())
                        			return new PositionTechniques(liste);
                    	}
                    }
                }
            }   
        }
        return null;
    }
}