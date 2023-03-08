/**
 * Fichier Difficultes.java représentant les trois difficultés proposées par notre jeu
 * @author celui qui a fait la classe doit s'ajouter ici
 */

// Package GitHub
package io.github.nurikabe;

/**
 * Classe de type Enum représentant les différentes difficultés du jeu
 */
public enum Difficultes {
   FACILE("Facile"),
   MOYEN("Moyen"),
   DIFFICILE("Difficile");

   /**
    * Variable d'instance représentant le nom d'affichage des difficultés
    */
   private final String nomDiff;
   
   /**
    * Constructeur de la difficulté
    * @param nomDiff le nom d'affichage de la difficulté
    */
   Difficultes(String nomDiff) {
      this.nomDiff = nomDiff;
   }

   /**
    * Méthode recupNomDiff qui renvoie le nom de la difficulté actuelle
    * @return le nom de la difficulté
    */
   public String recupNomDiff() {
      return nomDiff;
   }
}