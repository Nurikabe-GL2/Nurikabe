package main.java.io.github.nurikabe.techniques;

import io.github.nurikabe.CaseNombre;
import io.github.nurikabe.CaseNormal;
//classe représentant la technique de continuité de l'ile, elle vérifie si une case vérifie la technique Éviter une zone de mur 2x2 et test si il y'a une case blanche entre cette dernière et une ile
public class ContinuiteDeLile implements Technique{
    /**
     * Méthode de parcours de la grille, elle vérifie si une case vérifie la technique Éviter une zone de mur 2x2 et test si il y'a une case blanche entre cette dernière et une ile
     * @param grille la grille en question
     * @return une Position technique ou null
     */
    @Override
    public PositionTechniques tester(Niveau grille)
    {
        int cpt;
        for(int i=0;i<grille.get_hauteur();i++,cpt=0)
        {
            for(int j=0;j<grille.get_largeur();j++)
            {
                if(grille.get(i).get(j).get_case() instanceof CaseNormal)
                {
                    List<Coup> liste = new ArrayList<>();
                    //si la case courante vérifie la technique de la zone de mur
                    //alors on vérifie s'il n'y à pas une case blanche qui est entre elle et une ile
                    if(EviterZoneMur(grille)!=null)
                    {
                        //si la case de gauche est valide et que c'est une case blanche et que la case de sa gauche est une ile
                        if((estCoordonneeValide(grille,i-1,j)&&grille.get(i-1).get(j).get_case().get_etat()=="b")&&(estCoordonneeValide(grille,i-2,j)&&grille.get(i-2).get(j).get_case() instanceof CaseNombre))
                            liste.add(new Coup(i,j));
                    	
                        //si la case de droite est valide et que c'est une case blanche et que la case de sa droite est une ile
                        else if((estCoordonneeValide(grille,i+1,j)&&grille.get(i+1).get(j).get_case().get_etat()=="b")&&(estCoordonneeValide(grille,i+2,j)&&grille.get(i+2).get(j).get_case() instanceof CaseNombre))
                            liste.add(new Coup(i,j));

                        //si la case en haut est valide et que c'est une case blanche et que la case en haut de cette dernière est une ile
                        else if((estCoordonneeValide(grille,i,j-1)&&grille.get(i).get(j-1).get_case().get_etat()=="b")&&(estCoordonneeValide(grille,i,j-2)&&grille.get(i).get(j-2).get_case() instanceof CaseNombre))
                            liste.add(new Coup(i,j));

                        //si la case en bas est valide et que c'est une case blanche et que la case en bas de cette dernière est une ile
                        else if((estCoordonneeValide(grille,i,j+1)&&grille.get(i).get(j+1).get_case().get_etat()=="b")&&(estCoordonneeValide(grille,i,j+2)&&grille.get(i).get(j+2).get_case() instanceof CaseNombre))
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