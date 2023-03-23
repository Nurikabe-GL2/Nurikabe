/**
 * Fichier Niveau.java représentant les niveaux
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX
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
import java.util.Scanner;

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
    Grille<String> grille_solution;

    /**
     * Variable d'instance panneauGrille représentant le panneau de la grille graphique
     */
    GridPane panneauGrille;

    /**
     * Variable d'instance sauvegarde représentant la sauvegarde de la grille
     */
    Sauvegarde sauvegarde;

   /**
    * Variable d'instance pileUndo représentant la pile servant au bouton Undo
    */
   private Pile pileUndo;
    
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
   private String nomNiveau;

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
    * Construteur de la classe Niveau
    * @param nomGrille le nom de la grille
    */
   public Niveau(Stage stage, String nom_niveau, String mode){
      this.stage=stage;
      this.nom_niveau=nom_niveau;
      this.mode_jeu=mode;
      this.sauvegarde=new Sauvegarde();
      this.panneauGrille = new GridPane();
      this.pileUndo = new Pile();
      this.pileRedo = new Pile();
      panneauGrille.getStylesheets().add("/css/Plateau.css");
      chargerGrille(nom_niveau);
   }

   /**
    * Méthode chargerGrille qui s'occupe de charger la grille
    * @param nomGrille le nom de la grille
    */
   public void chargerGrille(String nomGrille) {
      try {
        charger_grille_solution(nomGrille);

        if(charger_niveau(nomNiveau)==0){

                grille=new Grille<>(grille_solution.getLargeur(), grille_solution.getHauteur());
                grilleGraphique=new Grille<>(grille_solution.getLargeur(), grille_solution.getHauteur());

                for (int y = 0; y < grilleGraphique.recupHauteur(); y++) {

                    for (int x = 0; x < grilleGraphique.recupLargeur(); x++) {
                        //Case une_case;

                        if(grille_solution.get(x, y).equals(".")||grille_solution.get(x, y).equals("n")){
                            grille.set(x, y, new CaseNormale(x, y));
                            //if(grille_solution.get(x, y).equals("."))grille_solution.get(x, y)=".";
                        }
                        else grille.set(x, y, new CaseNombre(x, y, Integer.parseInt(grille_solution.get(x, y))));

                        grille_graphique.set(x, y, new CaseGraphique(x, y, 30, 30, this));
                        //System.out.println(grille.get(i).get(j).get_pane()!=null);
                        GridPane.setRowIndex(grille_graphique.get(x, y).get_pane(), y);
                        GridPane.setColumnIndex(grille_graphique.get(x, y).get_pane(), x);

                        gridpane.getChildren().addAll(grille_graphique.get(x, y).get_pane());
                        //lecture.close();
                    }
                }
            }
            else {
                grille_graphique=new Grille<>(grille_solution.getLargeur(), grille_solution.getHauteur());

                for (int y = 0; y < grille_graphique.getHauteur(); y++) {

                    for (int x = 0; x < grille_graphique.getLargeur(); x++) {



                        grille_graphique.set(x, y, new CaseGraphique(x, y, 30, 30, this));
                        //System.out.println(grille.get(i).get(j).get_pane()!=null);
                        GridPane.setRowIndex(grille_graphique.get(x, y).get_pane(), y);
                        GridPane.setColumnIndex(grille_graphique.get(x, y).get_pane(), x);

                        gridpane.getChildren().addAll(grille_graphique.get(x, y).get_pane());
                    }

                }
            }
      }catch (Exception e){
        e.printStackTrace();
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
            int largeur = lecture.nextInt();
            int hauteur = lecture.nextInt();
            grille_solution=new Grille<>(largeur, hauteur);

                    for (int y = 0; y < grille_solution.getHauteur(); y++) {

                        for (int x = 0; x < grille_solution.getLargeur(); x++) {

                            grille_solution.set(x, y, lecture.next());
                            if(grille_solution.get(x, y).equals("b"))grille_solution.set(x, y, ".");
                        }
                    }

            }catch (Exception e){
            System.out.println("erreur lors de la lecture de la grille : "+e);
          }
    }

    public static Grille<String> charger_grille_solution_statique(String name){
        try {

            FileInputStream fichier = new FileInputStream(name);
            Scanner lecture = new Scanner(fichier);
            int largeur = lecture.nextInt();
            int hauteur = lecture.nextInt();
            Grille<String> grille_sol=new Grille<>(largeur, hauteur);

            for (int y = 0; y < grille_sol.getHauteur(); y++) {

                for (int x = 0; x < grille_sol.getLargeur(); x++) {

                        grille_sol.set(x, y, lecture.next());

                        }
                    }
                    return grille_sol;
            }catch (Exception e){
            System.out.println("erreur lors de la lecture de la grille : "+e);
          }
         return null;
        }

    /**
     * Méthode victoire qui teste si la grille est terminée
     */
    public void victoire(){
        int count=0;
          for (int y = 0; y < grille_solution.getHauteur(); y++) {

                for (int x = 0; x < grille_solution.getLargeur() ; x++) {
                    if(grille_solution.get(x, y).equals(grille.get(x, y).get_cont_case()))count++;
                }
            }
            System.out.println("count : "+count+"\n l*L : "+grille_solution.getHauteur()*grille_solution.getLargeur());
      if(count==(grille_solution.getHauteur()*grille_solution.getLargeur())){
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
    public static int verifier_grilles(Grille grille1, Grille grille2){
        int count=0;
        for (int y = 0; y < grille1.getHauteur(); y++) {

            for (int x = 0; x < grille2.getLargeur() ; x++) {
                if(grille1.get(x, y).equals(grille2.get(x, y)))count++;
            }
        }
            System.out.println("count : "+count+"\n l*L : "+grille1.getHauteur()*grille1.getLargeur());
      if(count==(grille1.getHauteur()*grille1.getLargeur())){
        return 1;
      }
      return 0;

    }

    public Case get_case(int x, int y){
        return grille.get(x,y);
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
        return grille.get(x, y).get_cont_case();
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
      return grille_solution.getLargeur();
   }

    /**
     * Méthode qui renvoie la hauteur de la grille
     * @return la hauteur de la grille
     */
    public int get_hauteur(){
        return grille_solution.getHauteur();
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