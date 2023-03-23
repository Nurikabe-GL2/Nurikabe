/**
 * Fichier CaseNormale.java héritant du fichier Case.java pour représenter les cases blanches ou noires
 * @author Lazare Maclouf
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;

/**
 * La classe CaseNormale hérite de la classe abstraite Case et représente les cases qui sont noires ou blanches
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
