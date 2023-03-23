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
     * getter renvoyant le type de la case ici 1 car c'est un nombre
     */
    public int get_type(){
      return type;
    }

    public int get_x(){
      return x;
    }

    public int get_y(){
      return y;
    }

    @Override
    public String toString(){
      return get_cont_case();
    }
    
    /**
     * getter qui renvoie le contenue de la case sous forme de chaine de caractère
     * @return le contenue de la case
     */
    public abstract String get_cont_case();

    public abstract void set_etat(int type);
    
}