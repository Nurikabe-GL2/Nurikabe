/**
 * Fichier ModesDeJeu.java représentant les trois modes de jeu proposés : classique, aventure et contre la montre
 * @author celui qui a fait la classe doit s'ajouter ici
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX
import javafx.scene.Node;
import javafx.scene.control.Toggle;

/**
 * Énumération public représentant le mode de jeu
 */
public enum ModesDeJeu {
    CLASSIQUE("classique", "classiqueToggle"),
    AVENTURE("aventure", "aventureToggle"),
    CONTRE_LA_MONTRE("montre", "montreToggle");

   /**
    * Variable d'instance représentant le nom du mode de jeu
    */
   private final String nomMode;
    
   /**
    * Variable d'instance représentant d'id du mode de jeu
    */
   private final String idMode;

   /**
    * Constructeur de la classe ModesDeJeu
    * @param nomMode le nom du mode de jeu à utiliser pendant les sauvegardes
    * @param idMode l'id du mode de jeu {@link Toggle}
    */
   ModesDeJeu(String nomMode, String idMode) {
      this.nomMode = nomMode;
      this.idMode = idMode;
   }

   /**
    * Méthode recupNomMode qui permet de récupérer le nom du mode de jeu
    * @return le nom du mode de jeu sous forme de chaine de caractère
    */
   public String recupNomMode() {
      return nomMode;
   }

   /**
    * Méthode recupIdMode qui permet de récupérer l'id du mode de jeu
    * @return l'id du mode de jeu sous forme de chaine de caractère
    */
   public String recupIdMode() {
      return idMode;
   }

   /**
    * Méthode fromToggle renvoyant le mode de jeu
    * @param toggle
    * @return le mode de jeu
    * @throws IllegalArgumentException exception renvoyé par la méthode en cas d'id incorrect
    */
   public static ModesDeJeu fromToggle(Toggle toggle) throws IllegalArgumentException {
      final String toggleId = ((Node) toggle).getId();
      for (ModesDeJeu jeuMode : values()) {
         if (jeuMode.idMode.equals(toggleId)) {
            return jeuMode;
         }
      }
      throw new IllegalArgumentException("Mot de jeu inconnu pour l'ID suivant : " + toggleId);
   }
}
