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
public class CaseNormale extends Case {
   /**
    * Variable représentant l'état de la case
    */
   int etat;

   /**
    * Variable d'instance représantant le panneau de la case
    */
   StackPane panneau;

   /**
    * Variable d'instance représentant la grille du jeu
    */
   Niveau grille;

   /**
    * Constructeur de la classe CaseNormale 
    * @param x la coordonnée x de la case
    * @param y la coordonnée y de la case
    * @param l la largeur de la case 
    * @param L la longueur de la case 
    * @param grid la grille de la case
    */
   public CaseNormale(int x, int y, int l, int L, Niveau grille) {
      super(x, y,0);
      this.etat = 0;
      this.grille = grille;
      panneau = new StackPane();
      panneau.getStyleClass().add("caseblanche");
      panneau.setPrefSize(l, L);
      panneau.setOnMouseClicked(e -> {
         System.out.println("Cliqué");
         actionClic(false);
      });
   }

   /**
    * Méthode gérant la réaction de la case au clic, elle s'occupe de changer l'état de la case de façon cyclique et vérifie si la grille est terminée
    * @param appel boolean pour savoir si la méthode est appelée par une autre méthode telle que Undo et Redo. Si c'est le cas, alors le coup ne sera pas push
    */
   @Override
   public void actionClic(boolean appel) {
      // Si la case contient un point
      if (etat == 2) {
         if (panneau.getChildren() != null)
            panneau.getChildren().remove(0);
         panneau.getStyleClass().remove(0);
         panneau.getStyleClass().add(0, "caseblanche");
         etat = 0;
      }
      
      // Si la case est noire
      else if (etat == 1) {    
         mettrePoint(1);
         etat = 2;
         grille.victoire();
      }

      // Si la case est blanche
      else { 
         //if (p.getChildren() != null) 
            //p.getChildren().remove(0);
         panneau.getStyleClass().remove(0);
         panneau.getStyleClass().add(0, "casenoire");
         etat = 1;
      }
      /*if (!appel)
         undoStack.push(jouer);*/
   }

   /**
    * Méthode recupCase qui renvoie le contenu de la case sous forme de chaine de caractère
    * @return le contenu de la case
    */
   @Override
   public String recupCase() {
      if (etat == 0)
         return "b";
      else if (etat == 1)
         return "n";
      else 
         return ".";
   }

   /**
    * Méthode recupPanneau qui renvoie le panneau de la case
    * @return le panneau de la case
    */
   @Override
   public StackPane recupPanneau() {
      return panneau;
   }

   /**
    * Methode privée à la classe pour mettre un point noir dans la case quand la case se trouve dans l'état point 
    * @param couleur integer représentant la couleur du point (0 pour gris, 1 pour noir)
    */
   private void mettrePoint(int couleur) {
      Circle cercle = new Circle(10, 10, 7);
      if (couleur == 1)
         cercle.setFill(Color.BLACK);
      else 
         cercle.setFill(Color.GREY);
      panneau.getStyleClass().remove(0);
      panneau.getChildren().add(cercle);
      panneau.getStyleClass().add(0, "caseblanche");
   }
}
