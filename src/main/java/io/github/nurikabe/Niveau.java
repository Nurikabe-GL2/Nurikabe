/**
 * Fichier Niveau.java représentant les grilles
 * @author celui qui a fait la classe doit s'ajouter ici
 */

// Package GitHub
package io.github.nurikabe;

//import org.slf4j.Logger;
// Importation des librairies javaFX
import java.io.FileInputStream;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import java.util.Scanner;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 

/**
 * Classe Niveau pour représenter une grille
 */
public class Niveau {
   //String grille[][];

   /**
    * Initialisation du logger pour générer des messages durant l'exécution suite à des évènements
    */
   //private static final Logger LOGGER = Logging.getLogger();

   /**
    * Variable d'instance qui représente le contenu de la grille sous forme d'une ArrayList
    */
   ArrayList<ArrayList<Case>> grille = new ArrayList<ArrayList<Case>>();
    
   /**
    * Variable d'instance représentant la solution de la grille
    */
   String grilleSolution[][];
    
   /**
    * Variable d'instance représentant la largeur de la grille
    */
   int largeur; 
    
   /**
    * Variable d'instance représentant la hauteur de la grille
    */
   int hauteur;
    
   /**
    * Variable d'instance représentant l'état de la partie (par défaut égal à 0)
    */
   int etatPartie = 0;
    
   /**
    * Variable d'instance grille_graphique représentant la grille graphique
    */
   GridPane grilleGraphique;

   /**
    * Variable d'instance représentant la pile servant au bouton Undo
    */
   private final Pile pileUndo;
    
   /**
    * Variable d'instance représentant la pile servant au bouton Redo
    */
   private final Pile pileRedo;

   /**
    * Construteur de la classe Niveau
    * @param nomGrille le nom de la grille
    */
   public Niveau(String nomGrille){
      grilleGraphique = new GridPane();
      grilleGraphique.getStylesheets().add("/css/Plateau.css");
      chargerGrille(nomGrille);
      this.pileUndo = new Pile();
      this.pileRedo = new Pile();
      //System.out.println("Working Directory = " + System.getProperty("user.dir"));
   }

   /**
    * Méthode chargerGrille qui s'occupe de charger la grille
    * @param nomGrille le nom de la grille
    */
   public void chargerGrille(String nomGrille) {
      int i;
      try {
         FileInputStream fichier = new FileInputStream(nomGrille);
         Scanner lecture = new Scanner(fichier);
         this.hauteur = lecture.nextInt();
         this.largeur = lecture.nextInt();
         grilleSolution = new String[largeur][hauteur];

         for (i = 0; i < largeur; i++) {
            grille.add(new ArrayList<Case>());
         }

         for (i = 0; i < largeur; i++) {
            for (int j = 0; j < hauteur; j++) {
            grilleSolution[i][j] = lecture.next();

            //Case uneCase;
        
            if (grilleSolution[i][j].equals("b") || grilleSolution[i][j].equals("n"))
               grille.get(i).add(new CaseNormale(i, j, 50, 50, this));
            else 
               grille.get(i).add(new CaseNombre(i, j, 50, 50, Integer.parseInt(grilleSolution[i][j])));

            //System.out.println(grille.get(i).get(j).recupPanneau() != null);
            GridPane.setRowIndex(grille.get(i).get(j).recupPanneau(), i);
            GridPane.setColumnIndex(grille.get(i).get(j).recupPanneau(), j);

            grilleGraphique.getChildren().addAll(grille.get(i).get(j).recupPanneau());
            //lecture.close();
            }
         }
      } catch (Exception e) {
         System.out.println("Erreur lors de la lecture de la grille : " +e);
      }
   }

   /**
    * Méthode victoire qui teste si la grille est terminée
    */
   public void victoire() {
      int i, j;
      int compteur = 0;
      for (i = 0; i < largeur; i++) {
         for (j = 0; j < hauteur; j++) {
               if (grilleSolution[i][j] == grille.get(i).get(j).recupCase())
                  compteur++;
         }
      }
      
      System.out.println("Compteur : " + compteur + "\n Largeur * Hauteur : " + largeur * hauteur);
      
      if (compteur == (largeur * hauteur)) {
        etatPartie = 1;
        System.out.println("Partie gagnée !");
      } else {
         etatPartie = 0;
      }

      for (i = 0; i < largeur; i++) {
         for (j = 0; j < hauteur; j++) {
            if (etatCase(i, j).equals(".") == true) {
               // A faire
            }
        }
      }
   }

   /**
    * Méthode verifierCasesAutour qui compte le nombre de cases autour de la grille 
    * @param x la coordonnée x de la case
    * @param y la coordonnée y de la case
    * @return le nombre de cases
    */
   public int verifierCasesAutour(int x, int y) {
      int compteur = 0;
      // Si la case est en haut à gauche
      if ((x - 1 < 0) && (y + 1 == hauteur)) {
         // if (etatCase(x))
      }

      // Si la case est en bas à gauche
      else if ((x - 1 < 0) && (y - 1 < 0)) {
      }

      // Si la case est en bas à droite
      else if ((x + 1 == largeur) && (y - 1 < 0)) {
      }
      
      // Si la case est en haut à droite
      else if ((x + 1 == largeur) && (y + 1 == hauteur)) {
      }

      // Si la case est sur la première ligne
      else if ((x + 1 < largeur) && (x - 1 >= 0) && (y + 1 == hauteur)) {
      }

      // Si la case est sur la dernière ligne
      else if ((x + 1 < largeur) && (x - 1 >= 0) && (y - 1 < 0)) {
      }

      // Si la case est sur la première colonne
      else if (x - 1 < 0) {
      }

      // Si la case est sur la dernière colonne
      else if (x + 1 == largeur) {
      }
      return compteur;
   }

   /**
    * Méthode etatCase qui renvoie l'état de la case
    * @param x la coordonné x de la case
    * @param y la coordonné y de la case
    * @return l'état de la case sous forme de chaine de caractère
    */
   public String etatCase(int x, int y) {
      return grille.get(x).get(y).recupCase();
   }

   /**
    * Méthode afficherGrille qui affiche la grille
    */
   public void afficherGrille() {
      int i, j;
      for (i = 0; i < largeur; i++) {
         for (j = 0; j < hauteur; j++) {
            System.out.print(grille.get(i).get(j).recupCase());
         }
         System.out.println("");
      }
   }

   /**
    * Méthode recupLargeur qui renvoie la largeur de la grille
    * @return la largeur de la grille
    */
   public int recupLargeur() {
      return largeur;
   }

   /**
    * Méthode recupHauteur qui renvoie la hauteur de la grille
    * @return la hauteur de la grille
    */
   public int recupHauteur() {
      return hauteur;
   }

   /**
    * Méthode recupEtatPartie qui renvoie l'état de la partie
    * @return l'état de la partie sous forme d'entier
    */
   public int recupEtatPartie() {
      return etatPartie;
   }

   /**
    * Méthode recupGrilleGraphique qui permet de récupérer la partie graphique de la grille
    * @return un gridPane qui représente la grille
    */
   public GridPane recupGrilleGraphique() {
      return this.grilleGraphique;
   }   

   /**
    * Méthode coup appelée par les handlers de Undo et Redo pour pop un coup le joué et le mettre dans la pile correcte
    * @param aPop la pile qui possède le coup à joué, c'est elle qui sera pop
    * @param aPush la pile qui recevra le nouveau coup, c'est elle qui sera push
    * @param nbClics le nombre de clics à faire pour revenir au coup (si c'est un coup précédent alors 2 sinon 1)
    */
   private void coup(Pile aPop, Pile aPush, int nbClics) {
      int i;
      Coup coupPris = new Coup(-1, -1);
      coupPris = aPop.depiler();
      if (coupPris.recupX() != -1) {
         for (i = 0; i < nbClics; i++)
            grille.get(coupPris.recupX()).get(coupPris.recupY()).actionClic(true);
      }
        aPush.empiler(coupPris);
   }

   /**
    * Le handler associé au bouton précédent
    */
   public EventHandler<MouseEvent> handlerUndo = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
         //LOGGER.info("Undo cliqué");
         coup(pileUndo, pileRedo,2);
      }
   };

   /**
    * Le handler associé au bouton suivant 
    */
   public EventHandler<MouseEvent> handlerRedo = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
         //LOGGER.info("Redo cliqué");
         coup(pileRedo, pileUndo,1);
      }
   };
}