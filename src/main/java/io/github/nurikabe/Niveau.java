/**
 * Fichier Niveau.java représentant les niveaux
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX
import io.github.nurikabe.controller.SelectionNiveauxController;
import javafx.stage.Modality;
import javafx.scene.layout.GridPane;

import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;


/**
 * Classe Niveau pour représenter un niveau
 */
public class Niveau implements Serializable {
   /**
    * Variable d'instance grille qui représente le contenu de la grille sous forme d'une ArrayList
    */
   Grille<Case> grille ;

   /**
    * Variable d'instance grilleGraphique qui représente la grille graphique
    */
    Grille<CaseGraphique> grilleGraphique;

    /**
     * Variable d'instance grilleSolution représentant la solution de la grille
     */
    Grille<String> grilleSolution;

    /**
     * Variable d'instance panneauGrille représentant le panneau de la grille graphique
     */
    GridPane panneauGrille = new GridPane();

    /**
     * Variable d'instance sauvegarde représentant la sauvegarde de la grille
     */
    Sauvegarde sauvegarde;

   /**
    * Variable d'instance pileUndo représentant la pile servant au bouton Undo
    */
   private Pile pileUndo;

    private SelectionNiveauxController select;
    
   /**
    * Variable d'instance pileRedo représentant la pile servant au bouton Redo
    */
   private Pile pileRedo;

   /**
    * Variable d'instance stage qui représente le plateau de jeu
    */
   Stage stage;

   /**
    * Variable d'instance nomNiveau qui représente le nom du niveau
    */
   private String cheminNiveau;

   /**
    * Variable d'instance modeDeJeu qui représente le mode de jeu
    */
   private String mode_jeu;

   /**
    * Variables d'instances undoB et redo B
    */
   Button undoB, redoB;

   private boolean etat_partie=false;

   /**
    * Constructeur de la classe Niveau
    * @param cheminNiveau le chemin vers la grille
    */
   public Niveau(Stage stage, String cheminNiveau, String mode, SelectionNiveauxController select) throws Exception{
        this.select=select;
      this.stage=stage;
      this.cheminNiveau = cheminNiveau;
      this.mode_jeu=mode;
       initialiser();
   }

    private void initialiser() throws Exception {
        this.sauvegarde=new Sauvegarde();
        this.panneauGrille.getChildren().clear();
        this.pileUndo = new Pile();
        this.pileRedo = new Pile();
        panneauGrille.getStylesheets().add("/css/Plateau.css");
        chargerGrille();
    }

    /**
    * Méthode chargerGrille qui s'occupe de charger la grille
    */
   public void chargerGrille() throws Exception {
       grilleSolution = chargerGrilleSolution(cheminNiveau);

        if(charger_niveau(cheminNiveau)==0){

                grille=new Grille<>(grilleSolution.recupLargeur(), grilleSolution.recupHauteur());
                grilleGraphique=new Grille<>(grilleSolution.recupLargeur(), grilleSolution.recupHauteur());

                for (int y = 0; y < grilleGraphique.recupHauteur(); y++) {

                    for (int x = 0; x < grilleGraphique.recupLargeur(); x++) {
                        //Case une_case;

                        if(grilleSolution.recup(x, y).equals("b")|| grilleSolution.recup(x, y).equals("n")){
                            grille.mettre(x, y, new CaseNormale(x, y));
                        }
                        else grille.mettre(x, y, new CaseNombre(x, y, Integer.parseInt(grilleSolution.recup(x, y))));

                        grilleGraphique.mettre(x, y, new CaseGraphique(x, y, 30, 30, this));
                        //System.out.println(grille.get(i).get(j).get_pane()!=null);
                        GridPane.setRowIndex(grilleGraphique.recup(x, y).recupPanneau(), y);
                        GridPane.setColumnIndex(grilleGraphique.recup(x, y).recupPanneau(), x);

                        panneauGrille.getChildren().addAll(grilleGraphique.recup(x, y).recupPanneau());
                        //lecture.close();
                    }
                }
            }
            else {
                grilleGraphique =new Grille<>(grilleSolution.recupLargeur(), grilleSolution.recupHauteur());

                for (int y = 0; y < grilleGraphique.recupHauteur(); y++) {

                    for (int x = 0; x < grilleGraphique.recupLargeur(); x++) {



                        grilleGraphique.mettre(x, y, new CaseGraphique(x, y, 30, 30, this));
                        //System.out.println(grille.get(i).get(j).get_pane()!=null);
                        GridPane.setRowIndex(grilleGraphique.recup(x, y).recupPanneau(), y);
                        GridPane.setColumnIndex(grilleGraphique.recup(x, y).recupPanneau(), x);

                        panneauGrille.getChildren().addAll(grilleGraphique.recup(x, y).recupPanneau());
                    }

                }
            }
    }

    public void sauvegarderNiveau(){
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try {
            File sauv =  new File("src/main/resources/sauvegarde/"+ cheminNiveau.substring(27)+mode_jeu);
            //System.out.println(nom_niveau+mode_jeu);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(sauv))) {
                sauvegarde.mettreGrille(grille);
                sauvegarde.setRedoPile(pileRedo);
                sauvegarde.mettrePileUndo(pileUndo);
                oos.writeObject(this.sauvegarde);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * Setter du bouton redo
     * @param b le bouton
     */
    public void setRedoB(Button b){
        redoB=b;
    }

    /**
     * Setter du bouton undo
     * @param b le bouton
     */
    public void setUndoB(Button b){
        undoB=b;
    }

    public int charger_niveau(String nom_niveau) throws Exception {
        File sauv = new File("src/main/resources/sauvegarde/" + nom_niveau.substring(27) + mode_jeu);
        if (sauv.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sauv))) {
                sauvegarde = (Sauvegarde) ois.readObject();
                grille = sauvegarde.recupGrille();
                pileUndo = sauvegarde.recupPileUndo();
                pileRedo = sauvegarde.recupPileRedo();
                return 1;
            }
        } else {
            return 0;
        }
    }

    public static Grille<String> chargerGrilleSolution(String cheminGrille) throws IOException {
        try (Scanner lecture = new Scanner(new FileInputStream(cheminGrille))) {
            int largeur = lecture.nextInt();
            int hauteur = lecture.nextInt();
            Grille<String> grille_sol=new Grille<>(largeur, hauteur);

            for (int y = 0; y < grille_sol.recupHauteur(); y++) {

                for (int x = 0; x < grille_sol.recupLargeur(); x++) {

                        grille_sol.mettre(x, y, lecture.next());

                        }
                    }
                    return grille_sol;
            }
        }

    /**
     * Méthode victoire qui teste si la grille est terminée
     */
    public void victoire() {
        final int erreurs = verifier();

      if(erreurs==0){
        try {
            File myFile = new File("src/main/resources/sauvegarde/"+ cheminNiveau.substring(27)+mode_jeu);
            myFile.delete();

            FileWriter sauv =  new FileWriter("src/main/resources/sauvegarde/"+ cheminNiveau.substring(27)+mode_jeu);
            sauv.write("NIVEAU_COMPLETE");
            sauv.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        etat_partie=true;

          // Create a label with the message
          Label messageLabel = new Label("Niveau complété !");


          // Create a VBox to hold the label and button HBox
          VBox vbox = new VBox(10);
          vbox.getChildren().addAll(messageLabel);
          // Create the dialog box
          Stage dialog = new Stage();
          dialog.initModality(Modality.APPLICATION_MODAL);
          dialog.initOwner(stage);
          dialog.setScene(new Scene(vbox));
          dialog.setTitle("Niveau Complété !");

          // Show the dialog box
          dialog.show();

        System.out.println("PARTIE GAGNEE !!!!");
        select.refreshLevels();
      }
    }

    public Case recupCase(int x, int y){
        return grille.recup(x,y);
    }

    /**
     * Méthode etatCase qui renvoie l'état de la case
     * @param x la coordonné x de la case
     * @param y la coordonné y de la case
     * @return l'état de la case sous forme de chaine de caractère
     */
    public String etat_case(int x, int y){
        return grille.recup(x, y).recupContenuCase();
    }

    /**
     * Méthode afficherGrille qui affiche la grille
     */
    public void afficher_grille(){
         System.out.println(grille);
    }

   /**
    * Méthode recupLargeur qui renvoie la largeur de la grille
    * @return la largeur de la grille
    */
   public int recupLargeur() {
      return grilleSolution.recupLargeur();
   }

    /**
     * Méthode qui renvoie la hauteur de la grille
     * @return la hauteur de la grille
     */
    public int get_hauteur(){
        return grilleSolution.recupHauteur();
    }

    /**
     * Méthode qui renvoie l'état de la partie
     * @return l'état de la partie sous forme d'entier
     */
    public GridPane getGridPane(){
        return this.panneauGrille;
    }

    public Grille<CaseGraphique> getGrilleGraphique() {
        return grilleGraphique;
    }

    /**
    * Méthode coup appelée par les handlers de Undo et Redo pour pop un coup le joué et le mettre dans la pile correcte
    * @param aPop la pile qui possède le coup à jouer, c'est elle qui sera pop
    * @param aPush la pile qui recevra le nouveau coup, c'est elle qui sera push
    * @param nbClics le nombre de clics à faire pour revenir au coup (si c'est un coup précédent alors 2 sinon 1)
    */
   private void coup(Pile aPop, Pile aPush, int nbClics) {
      int i;
      Coup coupPris = new Coup(-1, -1);
      coupPris = aPop.depiler();
      if (coupPris.recupX() != -1) {
         for (i = 0; i < nbClics; i++)
            grilleGraphique.recup(coupPris.recupX(), coupPris.recupY()).actionClic();
      }
        aPush.empiler(coupPris);
   }

   /**
    * Le handler associé au bouton précédent
    */
   public EventHandler<MouseEvent> handlerUndo = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
            coup(pileUndo, pileRedo,2);
      }
   };

    public boolean recupEtatPartie() {
        return etat_partie;
    }

    /**
     * Le handler associé au bouton suivant
     */
    public EventHandler<MouseEvent> handlerRedo = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            coup(pileRedo, pileUndo, 1);
        }
    };

    /**
     * Getter du stack Undo
     * @return une pile
     */
    public Pile recupUndo() {
        return pileUndo;
    }

    /**
     * Calcule le nombre d'erreurs et le renvoie
     *
     * @return le nombre d'erreurs
     */
    public int verifier() {
        int erreurs = 0;
        for (int x = 0; x < grille.recupLargeur(); x++) {
            for (int y = 0; y < grille.recupHauteur(); y++) {
                String contenuGrille = grille.recup(x, y).recupContenuCase();
                if (contenuGrille.equals(".")) contenuGrille = "b";

                final String contenuSolution = grilleSolution.recup(x, y);
                if (!contenuGrille.equals(contenuSolution)) {
                    erreurs++;
                }
            }
        }

        return erreurs;
    }

    public void reset() {
        try {
            //Voir #charger_niveau
            File sauvegarde = new File("src/main/resources/sauvegarde/" + cheminNiveau.substring(27) + mode_jeu);
            if (sauvegarde.exists()) {
                if (!sauvegarde.delete()) throw new IOException("Unable to delete " + sauvegarde);
            }

            initialiser();
            sauvegarderNiveau();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}