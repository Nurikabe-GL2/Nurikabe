package io.github.nurikabe.techniques;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Position;

import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;
/**
 * Classe public représentant les différentes techniques
 */
public class IleDeUn implements Technique {
    
    /**
     * La techniques recherche les cases de nombres avec le chiffre un,
     * Et retourne la position du dit un
     * @return la position du 1
     */
    @Override
    public PositionTechniques tester(Niveau grille)
    {
        
        for(int i=0;i<grille.get_hauteur();i++)
        {
            for(int j=0;j<grille.get_largeur();j++)
            {
                if(grille.get(i).get(j).get_case().equals("1"))
                {
                    List<Coup> liste = new ArrayList<>();

                    if(estValide(grille,i-1,j))
                        liste.add(new Coup(i-1, j));
                    if(estValide(grille,i+1,j))
                        liste.add(new Coup(i+1, j));
                    if(estValide(grille,i,j-1))
                        liste.add(new Coup(i, j-1));
                    if(estValide(grille,i,j+1))
                        liste.add(new Coup(i, j+1));
                    if(!liste.isEmpty())
                        return new PositionTechniques(liste);
                }
            }   
        }
        return null;
    }
}
