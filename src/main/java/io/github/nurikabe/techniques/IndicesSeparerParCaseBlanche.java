package io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la techniques de jeux pour 2 cases de nombe séparer par un case blanche
 */
public class IndicesSeparerParCaseBlanche implements Technique {
    
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
                if(grille.get_case(i, j) instanceof CaseNombre)
                {
                    List<Coup> liste = new ArrayList<>();

                    if(estValideCaseNombreEspace(grille,i-2,j))
                        liste.add(new Coup(i-1, j));
                    if(estValideCaseNombreEspace(grille,i+2,j))
                        liste.add(new Coup(i+1, j));
                    if(estValideCaseNombreEspace(grille,i,j-2))
                        liste.add(new Coup(i, j-1));
                    if(estValideCaseNombreEspace(grille,i,j+2))
                        liste.add(new Coup(i, j+1));
                    if(!liste.isEmpty())
                        return new PositionTechniques(liste);
                }
            }   
        }
        return null;
    }
}