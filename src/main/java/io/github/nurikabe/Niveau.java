package io.github.nurikabe;

import java.util.ArrayList;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.util.Scanner;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 
import javafx.stage.Stage;
import java.io.*;
import java.util.*;
import javafx.scene.text.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;


/**
 * Classe public représentant une grille
 */
public class Niveau implements Serializable{

    /**
     * initialisation du logger pour générer des messages durant l'éxécution suite à des évènements.
     */

    /**
     * variable d'instance qui représente le contenue de la grille sous forme d'unne ArrayList
     */
    Grille<Case> grille;

    Grille<CaseGraphique> grille_graphique;
    
    /**
     * variable d'instance représentant la solution de la grille
     */
    Grille<String> grille_solution;
    
    /**
     * un gridPane représentant la grille
     */
    GridPane gridpane;
    
    Sauvegarde sauvegarde;

    /**
     * La pile servant au bouton undo
     */
    private Pile undoStack;
    
    /**
     * La pile servant au bouton redo
     */
    private Pile redoStack;

    Stage stage;

    private String nom_niveau;

    private String mode_jeu;

    Button undoB, redoB;

    private boolean etat_partie=false;

    /**
     * Le construteur de la grille
     * @param name le nom de la grille
     */
    public Niveau(Stage stage, String nom_niveau, String mode){
        this.stage=stage;
        this.nom_niveau=nom_niveau;
        this.mode_jeu=mode;
        this.sauvegarde=new Sauvegarde();
        this.gridpane = new GridPane();
        this.undoStack = new Pile();
        this.redoStack = new Pile();
        gridpane.getStylesheets().add("/css/Plateau.css");
        charger_grille(nom_niveau);
    }

    /**
     * Méthode public qui s'occupe de chargé la grille
     * @param name le nom de la grille
     */
    public void charger_grille(String name){
      try {
        charger_grille_solution(name);

        if(charger_niveau(nom_niveau)==0){

                grille=new Grille<>(grille_solution.getLargeur(), grille_solution.getHauteur());
                grille_graphique=new Grille<>(grille_solution.getLargeur(), grille_solution.getHauteur());

                for (int y = 0; y < grille_graphique.getHauteur(); y++) {
               
                    for (int x = 0; x < grille_graphique.getLargeur(); x++) {
                        
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
            sauvegarde.setRedoPile(redoStack);
            sauvegarde.setUndoPile(undoStack);
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
                undoStack=sauvegarde.get_undo_pile();
                redoStack=sauvegarde.get_redo_pile();
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
     * Méthode qui test si la grille est fini
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
     * Méthode qui compte le nombre de case autour de la grille 
     * @param x la coordonné x de la case
     * @param y la coordonné y de la case
     * @return le nombre de case
     */
    public int verifier_cases_autour(int x, int y){
        int count=0;
        return count;
    }

    /**
     * méthode public qui renvoie l'état de la case
     * @param x la coordonné x de la case
     * @param y la coordonné y de la case
     * @return l'état de la case sous forme de chaine de caractère
     */
    public String etat_case(int x, int y){
        return grille.get(x, y).get_cont_case();
    }

    /**
     * méthode public qui affiche la grille
     */
    public void afficher_grille(){
         System.out.println(grille);
    }

    /**
     * getter de la largeur de la grille
     * @return la largeur de la grille
     */
    public int get_largeur(){
        return grille_solution.getLargeur();
    }

    /**
     * getter de la hauteur de la grille
     * @return la hauteur de la grille
     */
    public int get_hauteur(){
        return grille_solution.getHauteur();
    }
    /**
     * getter de la partie graphique de la grille
     * @return un gridPane
     */
    public GridPane get_grillegraphique(){
        return this.gridpane;
    }
    

    /**
     * Méthode appelé par les handlers de undo et redo pour pop un coup le joué un coup et le mettre dans la pile correcte
     * @param a_pop la pile qui possède le coup à jouer, c'est elle qui sera pop
     * @param a_push la pile qui recevra le nouveau coup, c'est elle qui sera push
     * @param nb_clique le nombre de clique à faire pour revenir au coup (si c'est un coup précédent alors 2 sinon 1)
     */
    private void coup(Pile a_pop, Pile a_push, int nb_clique){
        
        Coup coup_pris = new Coup(-1,-1);

        coup_pris=a_pop.pop();

        if(coup_pris.get_x()!=-1)
        {
            for(int i=0;i<nb_clique;i++)
                grille_graphique.get(coup_pris.get_x(), coup_pris.get_y()).action_clic();
        }

        a_push.push(coup_pris);
    }

    /**
     * Le handler associé au bouton précédent 
     */
    public EventHandler<MouseEvent> handlerUndo = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            //LOGGER.info("undo cliqué");
            undoB.setStyle("-fx-background-color: #00008B");
            coup(undoStack, redoStack,2);
        }
    };

    public boolean get_etat_partie(){
        return etat_partie;
    }

    /**
     * Le handler associé au bouton suivant 
     */
    public EventHandler<MouseEvent> handlerRedo = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            //LOGGER.info("redo cliqué");
            //if(e.getEventType().getName().equals("MOUSE_RELEASED"))redoB.setStyle("-fx-background-color: #00008B");
            redoB.setStyle("-fx-background-color: #00008B");
            coup(redoStack, undoStack,1);
        }
    };

    public Pile getUndo()
    {
        return undoStack;
    }

}