package io.github.nurikabe;

import javafx.scene.layout.*;
import java.io.*;


/**
 * Classe abstraite représentant une case
 */
public abstract class Case implements Serializable{

    /**
     * coordonnée x de la case
     */
    int x;

    /**
     * coordonnée y de la case
     */
    int y;

    /**
     * le type de la case
     */
    int type;

    //new Button();

    /**
     * Le construteur de la classe Case
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     */
    public Case(int x, int y, int type){
      this.x=x;
      this.y=y;
      this.type=type;
    } 

    /**
     *  prototype de la méthode qui défini la réaction de la case quand on clique dessus 
     * @param b
     */
    public abstract void action_clic(boolean b);
    
    /**
     * getter renvoyant le type de la case ici 1 car c'est un nombre
     */
    public int get_type(){
      return type;
    }
    
    /**
     * getter qui renvoie le contenue de la case sous forme de chaine de caractère
     * @return le contenue de la case
     */
    public abstract String get_case();
    
    /**
     * getter qui renvoie le pane de la case
     * @return le pane de la case
     */
    public abstract StackPane get_pane();
}