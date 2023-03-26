package io.github.nurikabe.techniques;

import io.github.nurikabe.Coup;
import io.github.nurikabe.Niveau;

import java.util.List;

//classe représentant la technique qui dit qu'il faut joindre deux murs si une case blanche est entre eux
public class ContinuiteDunMur extends Technique {
    @Override
    protected String getIdentifiant() {
        return "basique_3";
    }

    /**
     * Méthode de parcours de la grille, elle vérifie qu'une case blanche possède éxactement 2 murs autour d'elle (sans les diagonales) et si c'est le cas elle renvoie une liste avec la position de cette case
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
                if(grille.recupCase(x, y).recupContenuCase().equals("b"))
                {
                    int cpt = 0;

		            //test si la case de gauche de la case courante est valide et que c'est une case noire
                    if (estCaseNoire(grille, x - 1, y))
                        cpt++;
                    
                    //test si la case de droite de la case courante est valide et que c'est une case noire
                    if (estCaseNoire(grille, x + 1, y))
                        cpt++;
                    
                    //test si la case en bas de la case courante est valide et que c'est une case noire
                    if (estCaseNoire(grille, x, y - 1))
                        cpt++;

                    //test si la case en bas de la case courante est valide et que c'est une case noire
                    if (estCaseNoire(grille, x, y + 1))
                        cpt++;
                    
                    //si elle possède exactement 2 murs comme voisins et que cela ne formera pas un carré,
                    // alors la technique est valide pour cette case
                    if (cpt == 2 && !seraUnCarre(grille, x, y)) {
                        return new PositionTechniques(this, List.of(new Coup(x, y)));
                    }
                }
            }
        
        }   
        return null;
    }
}
