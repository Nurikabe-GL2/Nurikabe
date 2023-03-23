/**
 * Fichier Sauvegarde.java pour sauvegarder la grille de jeu et les piles d'undo-redo
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX
import java.util.ArrayList;
import java.io.*;

/**
 * Classe Sauvegarde représentant une grille
 */
public class Sauvegarde implements Serializable {
   /**
    * Variable d'instance grille qui représente le contenu de la grille sous forme d'une ArrayList
    */
   ArrayList<ArrayList<Case>> grille = new ArrayList<ArrayList<Case>>();
   
   /**
    * Variable d'instance pileUndo qui représente la pile d'undo
    */
   private Pile pileUndo;
   
   /**
    * Variable d'instance pileRedo qui représente la pile de redo
    */ 
   private Pile pileRedo;

   /**
    * Constructeur de Sauvegarde
    */
   public Sauvegarde() {
      this.pileUndo = null;
      this.pileRedo = null;
      this.grille = null;
   }

   /**
    * Méthode mettreGrille qui permet de récupérer une grille pour la mettre dans la nôtre
    * @param grille la grille à récupérer
    */ 
   public void mettreGrille(ArrayList grille){
      this.grille = grille;
   }

   /**
    * Méthode mettrePileUndo qui permet de récupérer une pile d'undo pour la mettre dans la nôtre
    * @param undo la pile undo à récupérer
    */
   public void mettrePileUndo(Pile undo) {
      this.pileUndo = undo;
   }

   /**
    * Méthode mettrePileRedoo qui permet de récupérer une pile de redo pour la mettre dans la nôtre
    * @param redo la pile redo à récupérer
    */
   public void mettrePileRedo(Pile redo) {
      this.pileRedo = redo;
   }
  
   /**
    * Méthode recupGrille qui renvoie la grille
    * @param grille la grille à renvoyer
    */ 
   public ArrayList recupGrille() {
      return grille;
   }

   /**
    * Méthode recupPileUndo qui renvoie la pile d'undo
    * @return la pile d'undo à renvoyer
    */
   public Pile recupPileUndo() {
      return pileUndo;
   }

   /**
    * Méthode recupPileRedo qui renvoie la pile de redo
    * @return la pile de redo à renvoyer
    */
   public Pile recupPileRedo() {
      return pileRedo;
   }
}