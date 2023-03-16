package io.github.nurikabe;

//import org.slf4j.Logger;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;

import java.util.Scanner;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 
import javafx.stage.Stage;
import java.io.*;


/**
 * Classe public représentant une grille
 */
public class Niveau implements Serializable{

    //String grille[][];

    /**
     * initialisation du logger pour générer des messages durant l'éxécution suite à des évènements.
     */
    //private static final Logger LOGGER = Logging.getLogger();

    /**
     * variable d'instance qui représente le contenue de la grille sous forme d'unne ArrayList
     */
    ArrayList<ArrayList<Case>> grille=new ArrayList<ArrayList<Case>>();

    ArrayList<ArrayList<CaseGraphique>> grille_graphique=new ArrayList<ArrayList<CaseGraphique>>();
    
    /**
     * variable d'instance représentant la solution de la grille
     */
    String grille_solution[][];
    
    /**
     * la largeur de la grille
     */
    int largeur; 
    
    /**
     * la hauteur de la grille
     */
    int hauteur;
    
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

    String nom_niveau;

    Button undoB, redoB;

    /**
     * Le construteur de la grille
     * @param name le nom de la grille
     */
    public Niveau(Stage stage, String nom_niveau){
        this.stage=stage;
        this.nom_niveau=nom_niveau;
        this.sauvegarde=new Sauvegarde();
        this.gridpane = new GridPane();
        this.undoStack = new Pile();
        this.redoStack = new Pile();
        gridpane.getStylesheets().add("/css/Plateau.css");
        charger_grille(get_niveau(nom_niveau));
    }

    /**
     * Méthode public qui s'occupe de chargé la grille
     * @param name le nom de la grille
     */
    public void charger_grille(String name){
      try {
        charger_grille_solution(name);
        
        for (int i = 0; i < largeur; i++) {
                    
            for (int j = 0; j < hauteur; j++) {
                //System.out.println(grille_solution[i][j]==null);
            }
        }

        if(charger_niveau("niveau1")==0){

                for(int i=0; i<largeur ; i++){
                    grille.add(new ArrayList<Case>());
                    grille_graphique.add(new ArrayList<CaseGraphique>());
                }

                for (int i = 0; i < largeur; i++) {
                    
                    for (int j = 0; j < hauteur; j++) {
                        
                        //Case une_case;
                    
                        if(grille_solution[i][j].equals("b")||grille_solution[i][j].equals("n")){
                            grille.get(i).add(new CaseNormale(i, j));
                            if(grille_solution[i][j].equals("b"))grille_solution[i][j]=".";
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
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try { 
            File sauv =  new File("src/main/resources/sauvegarde/"+nom_niveau);
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
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try { 
            File sauv =  new File("src/main/resources/sauvegarde/"+nom_niveau);
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
            this.hauteur = lecture.nextInt();
            this.largeur = lecture.nextInt();
            grille_solution=new String[largeur][hauteur];
    
                    for (int i = 0; i < largeur; i++) {
                        
                        for (int j = 0; j < hauteur; j++) {
                        
                            grille_solution[i][j] = lecture.next();
                            
                        }
                    }

            }catch (Exception e){
            System.out.println("erreur lors de la lecture de la grille : "+e);
          }
    }

    /**
     * Méthode qui test si la grille est fini
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
        stage.close();
        System.out.println("PARTIE GAGNEE !!!!");
      }
    
    }

    public Case get_case(int x, int y){
        return grille.get(x).get(y);
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
        return grille.get(x).get(y).get_cont_case();
    }

    /**
     * méthode public qui affiche la grille
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
     * getter de la largeur de la grille
     * @return la largeur de la grille
     */
    public int get_largeur(){
        return largeur;
    }

    /**
     * getter de la hauteur de la grille
     * @return la hauteur de la grille
     */
    public int get_hauteur(){
        return hauteur;
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
                grille_graphique.get(coup_pris.get_x()).get(coup_pris.get_y()).action_clic();
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


    public String get_niveau(String nom_niveau){
        switch(nom_niveau){
            case "niveau1": return "src/main/resources/niveaux/facile_03.txt";
                            //break;
            default : return "";
                        //break;
        }
    }
}