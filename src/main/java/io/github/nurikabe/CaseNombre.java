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
     * Constructeur de la classe Nombre
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     * @param l la longeur de la case 
     * @param L la largeur de la case 
     * @param nombre le nombre de la case
     */
    public CaseNombre(int x, int y, int nombre){
      
      super(x, y, 1);
      this.nombre=nombre;
    }

    /**
     * getter renvoyant le contenue de la case sous forme de chaine de caractère
     * @return le contenue de la case
     */
    @Override
    public String get_cont_case(){
      return Integer.toString(nombre);
    }

    public void set_etat(int type){
      return;
    }
  }