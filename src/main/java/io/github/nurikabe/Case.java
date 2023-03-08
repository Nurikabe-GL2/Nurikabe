/**
 * Fichier Case.java contenant la classe Case pour représenter une case de la grille Nurikabe
 * @author Lazare Maclouf
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX
import javafx.scene.layout.*;

/**
 * Classe abstraite représentant une case
 */
public abstract class Case {
   /**
    * Coordonnée x de la case
    */
   int x;

   /**
    * Coordonnée y de la case
    */
   int y;

   /**
    * Le type de la case
    */
   int type;

   //new Button();

   /**
    * Construteur de la classe Case
    * @param x la coordonnée x de la case
    * @param y la coordonnée y de la case
    * @param type le type de la case (soit noire, soit blanche, soit un chiffre)
    */
   public Case(int x, int y, int type) {
      this.x = x;
      this.y = y;
      this.type = type;
   } 

   /**
    * Méthode abstraite qui définit la réaction de la case quand on clique dessus
    * @param b False si la case est normale, True si la case est un nombre
    */
   public abstract void actionClic(boolean b);
    
    /**
     * Méthode recupType renvoyant le type de la case (ici 1 car c'est un nombre)
     */
   public int recupType() {
      return type;
   }
    
   /**
    * Méthode recupCase qui renvoie le contenu de la case sous forme de chaine de caractère
    * @return le contenu de la case
    */
   public abstract String recupCase();
    
   /**
    * Méthode recupPanneau qui renvoie le panneau de la case
    * @return le panneau de la case
    */
   public abstract StackPane recupPanneau();
}