package io.github.nurikabe;

import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;

/**
 * La classe CaseNormale héritant de la classe abstraite Case 
 */
public class CaseNormale extends Case{
    

    /**
     * le constructeur de la classe CaseNormale 
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     */
    public CaseNormale(int x, int y){
    
      super(x, y, 0);
    }

    /**
     * getter renvoyant l'état de la case sous forme de chaine de caractère
     * @return l'état de la case
     */
    @Override
    public String get_cont_case(){
     
      if(type==0)return "b";
     
      else if(type==-1)return "n";
     
      else return ".";

    }

    public void set_etat(int type){
      this.type=type;
    }
}
