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
public class Sauvegarde implements Serializable{
    /**
     * Variable d'instance grille qui représente le contenu de la grille sous forme d'une ArrayList
     */
    Grille<Case> grille;

    /**
     * Variable d'instance pileUndo qui représente la pile d'undo
     */
    private Pile pileUndo;

    /**
     * Variable d'instance chrono qui représente le chronomètre de la partie
     */
    private Chronometre chrono;

    /**
     * Variable d'instance pileRedo qui représente la pile de redo
     */
    private Pile pileRedo;

    private Score score;
    

   /**
    * Constructeur de Sauvegarde
    */
   public Sauvegarde() {
      this.pileUndo = null;
      this.pileRedo = null;
      this.grille = null;
      this.chrono = null;
      this.score = null;
   }

   /**
    * Méthode mettreGrille qui permet de récupérer une grille pour la mettre dans la nôtre
    * @param grille la grille à récupérer
    */
   public void mettreGrille(Grille<Case> grille){
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
    * setter pour la pile redo à sauvegarder
    * @param redo pile redo
    */
    public void setRedoPile(Pile redo){
        this.pileRedo=redo;
    }
    /**
    * setter pour le chronometre à sauvegarder
    * @param chrono chronometre à sauvegarder
    */
    public void setChrono(Chronometre chrono){
      this.chrono=chrono;
    }
   /**
    * setter pour le score
    * @param score score à sauvegarder
    */
    public void setScore(Score score){
      this.score=score;
    }
    /**
    * recupérer le chrono de la sauvegarde
    * @return chrono
    */
    public Chronometre recupChrono(){
      chrono.reset();
      return chrono;
    }
    /**
     * récuperer le score de la sauvegarde
     * @return score
     */
    public Score getScore(){
      return score;
    }
    /**
     * récuperer la grille
     * @return une Grille<Case>
     */
    public Grille<Case> recupGrille(){
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