/**
 * Fichier Niveau.java représentant les grilles
 * @author celui qui a fait la classe doit s'ajouter ici
 */

// Package GitHub
package io.github.nurikabe;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe Niveau pour représenter une grille
 */
public class Niveau implements Serializable{
    /**
     * Variable d'instance qui représente le contenu de la grille sous forme d'une ArrayList
     */
    ArrayList<ArrayList<Case>> grille=new ArrayList<ArrayList<Case>>();

    ArrayList<ArrayList<CaseGraphique>> grille_graphique=new ArrayList<ArrayList<CaseGraphique>>();
    
   /**
    * Variable d'instance représentant la solution de la grille
    */
   String grille_solution[][];
    
   /**
    * Variable d'instance représentant la largeur de la grille
    */
   int largeur;
    
   /**
    * Variable d'instance représentant la hauteur de la grille
    */
   int hauteur;

    /**
     * Variable d'instance représentant la grille graphique
     */
    GridPane gridpane;

    Sauvegarde sauvegarde;

   /**
    * Variable d'instance représentant la pile servant au bouton Undo
    */
   private Pile pileUndo;
    
   /**
    * Variable d'instance représentant la pile servant au bouton Redo
    */
   private Pile pileRedo;

    Stage stage;

    private String nom_niveau;

    private String mode_jeu;

    Button undoB, redoB;

    private boolean etat_partie=false;

    /**
     * Construteur de la classe Niveau
     * @param nomGrille le nom de la grille
     */
    public Niveau(Stage stage, String nom_niveau, String mode){
        this.stage=stage;
        this.nom_niveau=nom_niveau;
        this.mode_jeu=mode;
        this.sauvegarde=new Sauvegarde();
        this.gridpane = new GridPane();
        this.pileUndo = new Pile();
        this.pileRedo = new Pile();
        gridpane.getStylesheets().add("/css/Plateau.css");
        chargerGrille(nom_niveau);
    }

   /**
    * Méthode chargerGrille qui s'occupe de charger la grille
    * @param nomGrille le nom de la grille
    */
   public void chargerGrille(String name) {
      try {
        charger_grille_solution(name);

        if(charger_niveau(nom_niveau)==0){

                for(int i=0; i<largeur ; i++){
                    grille.add(new ArrayList<Case>());
                    grille_graphique.add(new ArrayList<CaseGraphique>());
                }

                for (int i = 0; i < largeur; i++) {

                    for (int j = 0; j < hauteur; j++) {

                        //Case une_case;

                        if(grille_solution[i][j].equals(".")||grille_solution[i][j].equals("n")){
                            grille.get(i).add(new CaseNormale(i, j));
                            //if(grille_solution[i][j].equals("."))grille_solution[i][j]=".";
                        }
                        else grille.get(i).add(new CaseNombre(i, j, Integer.parseInt(grille_solution[i][j])));

                        grille_graphique.get(i).add(new CaseGraphique(i, j, 30, 30, this));
                        //System.out.println(grille.get(i).get(j).get_pane()!=null);
                        GridPane.setRowIndex(grille_graphique.get(i).get(j).get_pane(), i);
                        GridPane.setColumnIndex(grille_graphique.get(i).get(j).get_pane(), j);

                        gridpane.getChildren().addAll(grille_graphique.get(i).get(j).get_pane());
                        //lecture.close();
                    }
                }
            }
            else {
                for(int i=0; i<largeur ; i++){
                    grille_graphique.add(new ArrayList<CaseGraphique>());
                }

                for (int i = 0; i < largeur; i++) {

                    for (int j = 0; j < hauteur; j++) {

                        grille_graphique.get(i).add(new CaseGraphique(i, j, 30, 30, this));
                        //System.out.println(grille.get(i).get(j).get_pane()!=null);
                        GridPane.setRowIndex(grille_graphique.get(i).get(j).get_pane(), i);
                        GridPane.setColumnIndex(grille_graphique.get(i).get(j).get_pane(), j);

                        gridpane.getChildren().addAll(grille_graphique.get(i).get(j).get_pane());

                    }

                }
            }
      }catch (Exception e){
        System.out.println("erreur lors de la lecture de la grille : "+e);
      }
    }

    public void sauvegarder_niveau(){
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try {
            File sauv =  new File("src/main/resources/sauvegarde/"+nom_niveau.substring(27)+mode_jeu);
            //System.out.println(nom_niveau+mode_jeu);
            sauv.createNewFile();
            ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(sauv));
            sauvegarde.setGrille(grille);
            sauvegarde.setRedoPile(pileRedo);
            sauvegarde.setUndoPile(pileUndo);
            oos.writeObject(this.sauvegarde);
        } catch (Exception e){
            System.out.println(e);
        }

    }

    public void setRedoB(Button b){
        redoB=b;
    }

    public void setUndoB(Button b){
        undoB=b;
    }

    public int charger_niveau(String nom_niveau){
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try {
            File sauv =  new File("src/main/resources/sauvegarde/"+nom_niveau.substring(27)+mode_jeu);
            if(sauv.createNewFile()==false){
                System.out.println("fichier existe deja");
                ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(sauv)) ;
                sauvegarde=(Sauvegarde)ois.readObject();
                grille=sauvegarde.get_grille();
                pileUndo =sauvegarde.get_undo_pile();
                pileRedo =sauvegarde.get_redo_pile();
                System.out.println(this.grille==null);
                return 1;
            }
            else return 0;
        } catch (Exception e){
            System.out.println(e);
            return 0;
        }

    }

    public void charger_grille_solution(String name){
        try {

            FileInputStream fichier = new FileInputStream(name);
            Scanner lecture = new Scanner(fichier);
            this.hauteur = lecture.nextInt();
            this.largeur = lecture.nextInt();
            grille_solution=new String[largeur][hauteur];

                    for (int i = 0; i < largeur; i++) {

                        for (int j = 0; j < hauteur; j++) {

                            grille_solution[i][j] = lecture.next();
                            if(grille_solution[i][j].equals("b"))grille_solution[i][j]=".";

                        }
                    }

            }catch (Exception e){
            System.out.println("erreur lors de la lecture de la grille : "+e);
          }
    }

    /**
     * Méthode victoire qui teste si la grille est terminée
     */
    public void victoire(){
        int count=0;
          for (int i = 0; i < largeur; i++) {
                for (int j = 0; j < hauteur; j++) {
                    if(grille_solution[i][j].equals(grille.get(i).get(j).get_cont_case())==true)count++;
                }
            }
            System.out.println("count : "+count+"\n l*L : "+largeur*hauteur);
      if(count==(largeur*hauteur)){
        try {
            File myFile = new File("src/main/resources/sauvegarde/"+nom_niveau.substring(27)+mode_jeu);
            myFile.delete();

            FileWriter sauv =  new FileWriter("src/main/resources/sauvegarde/"+nom_niveau.substring(27)+mode_jeu);
            //System.out.println(nom_niveau+mode_jeu);
            if(sauv!=null){
                sauv.write("NIVEAU_COMPLETE");
                sauv.close();
            }
        } catch (Exception e){
            System.out.println(e);
        }
        etat_partie=true;

          // Create a label with the message
          Label messageLabel = new Label("Niveau complété !");

          // Create buttons
          Button nextLevelButton = new Button("Niveau Suivant");
          Button mainMenuButton = new Button("Retour");

          // Create an HBox to hold the buttons
          HBox buttonBox = new HBox(10);
          buttonBox.getChildren().addAll(nextLevelButton, mainMenuButton);

          // Create a VBox to hold the label and button HBox
          VBox vbox = new VBox(10);
          vbox.getChildren().addAll(messageLabel, buttonBox);
          vbox.setPadding(new Insets(10));

          // Create the dialog box
          Stage dialog = new Stage();
          dialog.initModality(Modality.APPLICATION_MODAL);
          dialog.initOwner(stage);
          dialog.setScene(new Scene(vbox));
          dialog.setTitle("Niveau Complété !");

          // Show the dialog box
          dialog.show();

        System.out.println("PARTIE GAGNEE !!!!");
      }
    }

       /**
     * Méthode statique qui test si la grille est fini
     */
    public static int verifier_grilles(String grille1[][], String grille2[][], int l, int h){
        int count=0;
          for (int i = 0; i < l; i++) {
                for (int j = 0; j < h; j++) {
                    if(grille1[i][j].equals(grille2[i][j])==true)count++;
                }
            }
            System.out.println("count : "+count+"\n l*L : "+l*h);
      if(count==(l*h)){
        return 1;
      }
      return 0;

    }

    public Case get_case(int x, int y){
        return grille.get(x).get(y);
    }

    /**
     * Méthode verifierCasesAutour qui compte le nombre de cases autour de la grille
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     * @return le nombre de cases
     */
    public int verifier_cases_autour(int x, int y){
        int count=0;
        return count;
    }

    /**
     * Méthode etatCase qui renvoie l'état de la case
     * @param x la coordonné x de la case
     * @param y la coordonné y de la case
     * @return l'état de la case sous forme de chaine de caractère
     */
    public String etat_case(int x, int y){
        return grille.get(x).get(y).get_cont_case();
    }

    /**
     * Méthode afficherGrille qui affiche la grille
     */
    public void afficher_grille(){
         for (int i = 0; i < largeur; i++) {
                for (int j = 0; j < hauteur; j++) {
                    System.out.print(grille.get(i).get(j).get_cont_case());
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
     * Méthode qui renvoie la hauteur de la grille
     * @return la hauteur de la grille
     */
    public int get_hauteur(){
        return hauteur;
    }

    /**
     * Méthode qui renvoie l'état de la partie
     * @return l'état de la partie sous forme d'entier
     */
    public GridPane get_grillegraphique(){
        return this.gridpane;
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
            grille_graphique.get(coupPris.recupX()).get(coupPris.recupY()).action_clic();
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
         undoB.setStyle("-fx-background-color: #00008B");
            coup(pileUndo, pileRedo,2);
      }
   };

   public boolean get_etat_partie(){
        return etat_partie;
    }/**
    * Le handler associé au bouton suivant
    */
   public EventHandler<MouseEvent> handlerRedo = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
         //LOGGER.info("Redo cliqué");
            //if(e.getEventType().getName().equals("MOUSE_RELEASED"))redoB.setStyle("-fx-background-color: #00008B");
            redoB.setStyle("-fx-background-color: #00008B");
         coup(pileRedo, pileUndo,1);
      }
   };public Pile getUndo()
    {
        return pileUndo;
    }
}