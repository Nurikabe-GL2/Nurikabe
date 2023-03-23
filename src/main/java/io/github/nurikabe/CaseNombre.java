/**
 * Fichier CaseNombre.java héritant du fichier Case.java pour représenter les cases à nombres
 * @author Lazare Maclouf
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * La classe CaseNombre hérite de la classe abstraite Case et représente les cases qui contiennent un nombre
 */
public class CaseNombre extends Case {
   /**
    * Variable d'instance représentant le nombre de la case
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