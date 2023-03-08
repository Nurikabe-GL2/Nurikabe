/**
 * Fichier Pile.java représentant une pile pour les coups joués
 * @author celui qui a fait la classe doit s'ajouter ici
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Classe Pile qui permet de manipuler les coups joués
 */
public class Pile {
   private final Stack<Coup> pile;

   /**
    * Constructeur de la classe Pile
    */
   Pile() {
        pile = new Stack<>();
   }

   /**
    * Méthode empiler permettant d'empiler un coup joué dans la pile locale
    * @param coup le coup à empiler
    */
   public void empiler(Coup coup) {
      this.pile.push(coup);
   }

   /**
    * Méthode depiler permettant de dépiler un coup joué dans la pile locale
    * @return le coup dépilé
    */
   public Coup depiler() {
      return this.pile.pop();
   }

   /**
    * Méthode echangePile pour échanger un coup entre 2 piles et renvoie le coup en question
    * La pile appelée aura son premier coup retiré pour l'insérer dans la pile passée en paramètre
    * @param autrePile la pile où empiler le coup
    * @return le coup à échanger 
    * @throws EmptyStackException si la pile appelée est vide
    */
   public Coup echangePile(Pile autrePile) throws EmptyStackException{
      Coup coup = new Coup(-1,-1);
      try {
         coup = this.depiler();
         autrePile.empiler(coup);
      } catch (Exception e) {
         System.out.println("Erreur lors du dépilement sur une pile, cette dernière est peut-être vide");
      }
      return coup;
   }
}