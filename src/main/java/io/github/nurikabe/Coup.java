/**
 * Fichier Coup.java représentant un coup joué par le joueur
 * @author Lazare Maclouf
 */

// Package GitHub
package io.github.nurikabe;

/**
 * Classe Coup représentant un coup joué servant pour le Undo Redo
 */
public class Coup {
   /**
    * Variable d'instance représentant la coordonnée x d'un coup joué
    */
   int x;

   /**
    * Variable d'instance représentant la coordonnée y d'un coup joué
    */
   int y;

   /**
    * Constructeur de la classe Coup
    * @param x la coordonnée x de l'endroit du coup joué
    * @param y la coordonnée y de l'endroit du coup joué
    */
   public Coup(int x, int y) {
      this.x = x;
      this.y = y;
   }

   /**
    * Méthode recupX qui renvoie la coordonnée x du coup joué
    * @return la coordonnée x du coup
    */
   int recupX() {
      return x;
   }

   /**
    * Méthode recupY qui renvoie la coordonnée y du coup joué
    * @return la coordonnée y du coup
    */
   int recupY() {
      return y;
   }

   /**
    * Méthode definirX qui permet de définir la coordonnée x
    * @param new_x la coordonnée x
    */
   void definirX(int new_x) {
      this.x = new_x;
   }

   /**
    * Méthode definirY qui permet de définir la coordonnée x
    * @param new_y la coordonnée y
    */
   void definirY(int new_y) {
      this.y = new_y;
   }
}