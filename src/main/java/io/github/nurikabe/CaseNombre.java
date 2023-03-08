package io.github.nurikabe;

import javafx.scene.layout.*;
import javafx.scene.text.Text;



/**
 * La classe CaseNombre héritant de la classe abstraite Case 
 */
public class CaseNombre extends Case{

    /**
     * variable d'instance représentant le nombre de la case
     */
    int nombre;

    /**
     * le pane de la case
     */
    StackPane pane;

    /**
     * Constructeur de la classe Nombre
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     * @param l la longeur de la case 
     * @param L la largeur de la case 
     * @param nombre le nombre de la case
     */
    public CaseNombre(int x, int y, int l, int L, int nombre){
      
      super(x, y,1);
      this.nombre=nombre;
      pane = new StackPane();
      Text nb = new Text(Integer.toString(nombre));
      pane.getChildren().add(nb); 
      pane.getStyleClass().add("caseblanche");
      pane.setPrefSize(l, L);

    }

    /**
     * Méthode qui défini le comportement de la case en cas de clique,
     * ici elle ne fait rien
     */
    @Override
    public void action_clic(boolean appel){
      System.out.println("rien faire");
    }

    /**
     * getter renvoyant le contenue de la case sous forme de chaine de caractère
     * @return le contenue de la case
     */
    @Override
    public String get_case(){
      return Integer.toString(nombre);
    }

    /**
     * Getter renvoyant le pane de la case
     *  @return le pane de la case
     */
    @Override
    public StackPane get_pane(){
      return pane;
    }
}