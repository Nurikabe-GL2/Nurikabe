package io.github.nurikabe;

import java.io.FileInputStream;
//import org.slf4j.Logger;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;


import java.util.Scanner;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 


/**
 * Classe public représentant une grille
 */
public class Niveau {

    //String grille[][];

    /**
     * initialisation du logger pour générer des messages durant l'éxécution suite à des évènements.
     */
    //private static final Logger LOGGER = Logging.getLogger();

    /**
     * variable d'instance qui représente le contenue de la grille sous forme d'unne ArrayList
     */
    ArrayList<ArrayList<Case>> grille=new ArrayList<ArrayList<Case>>();
    
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
     * l'état de la partie de base à 0
     */
    int etat_partie=0;
    
    /**
     * un gridPane représentant la grille
     */
    GridPane grille_graphique;

    /**
     * La pile servant au bouton undo
     */
    private final Pile undoStack;
    
    /**
     * La pile servant au bouton redo
     */
    private final Pile redoStack;

    /**
     * Le construteur de la grille
     * @param name le nom de la grille
     */
    public Niveau(String name){
        grille_graphique = new GridPane();
        grille_graphique.getStylesheets().add("/css/Plateau.css");
        charger_grille(name);
        this.undoStack = new Pile();
        this.redoStack = new Pile();
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }

    /**
     * Méthode public qui s'occupe de chargé la grille
     * @param name le nom de la grille
     */
    public void charger_grille(String name){
      try {
        FileInputStream fichier = new FileInputStream(name);
        Scanner lecture = new Scanner(fichier);
        this.hauteur = lecture.nextInt();
        this.largeur = lecture.nextInt();
        grille_solution=new String[largeur][hauteur];
        

       

        for(int i=0; i<largeur ; i++){
            grille.add(new ArrayList<Case>());
        }

      for (int i = 0; i < largeur; i++) {
        
         for (int j = 0; j < hauteur; j++) {
           
            grille_solution[i][j] = lecture.next();
            
            
            //Case une_case;
        
            if(grille_solution[i][j].equals("b")||grille_solution[i][j].equals("n")){
                 grille.get(i).add(new CaseNormale(i, j, 50, 50, this));
            }
            else grille.get(i).add(new CaseNombre(i, j, 50, 50, Integer.parseInt(grille_solution[i][j])));

            //System.out.println(grille.get(i).get(j).get_pane()!=null);
            GridPane.setRowIndex(grille.get(i).get(j).get_pane(), i);
            GridPane.setColumnIndex(grille.get(i).get(j).get_pane(), j);

            grille_graphique.getChildren().addAll(grille.get(i).get(j).get_pane());
            //lecture.close();
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
                    if(grille_solution[i][j] == grille.get(i).get(j).get_case())count++;
                }
            }
            System.out.println("count : "+count+"\n l*L : "+largeur*hauteur);
      if(count==(largeur*hauteur)){
        etat_partie=1;
        System.out.println("PARTIE GAGNEE !!!!");
      }
      else etat_partie=0;
    
      for(int i=0; i<largeur; i++){

        for(int j=0; j<hauteur; j++){
            
            if(etat_case(i,j).equals(".")==true){

                //à faire;
            }

        }

      }
    
    }

    /**
     * Méthode qui compte le nombre de case autour de la grille 
     * @param x la coordonné x de la case
     * @param y la coordonné y de la case
     * @return le nombre de case
     */
    public int verifier_cases_autour(int x, int y){
        int count=0;
        //si la case est en haut à gauche
        if(x-1<0&&y+1==hauteur){
            //if(etat_case(x))
        }

        //si la case est en bas à gauche
        else if(x-1<0&&y-1<0){
            
        }

        //si la case est en bas à droite
        else if(x+1==largeur&&y-1<0){
            
        }
        
        //si la case est en haut à droite
        else if(x+1==largeur&&y+1==hauteur){
            
        }

        //si la case est sur la première ligne
        else if(x+1<largeur&&x-1>=0&&y+1==hauteur){
            
        }

        //si la case est sur la dernière ligne
        else if(x+1<largeur&&x-1>=0&&y-1<0){
            
        }

        //si la case est sur la première colonne
        else if(x-1<0){
            
        }

        //si la case est sur la dernière colonne
        else if(x+1==largeur){
            
        }
        return count;
    }

    /**
     * méthode public qui renvoie l'état de la case
     * @param x la coordonné x de la case
     * @param y la coordonné y de la case
     * @return l'état de la case sous forme de chaine de caractère
     */
    public String etat_case(int x, int y){
        return grille.get(x).get(y).get_case();
    }

    /**
     * méthode public qui affiche la grille
     */
    public void afficher_grille(){
         for (int i = 0; i < largeur; i++) {
                for (int j = 0; j < hauteur; j++) {
                    System.out.print(grille.get(i).get(j).get_case());
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
     * getter de l'état de la partie
     * @return l'état de la partie sous forme d'entier
     */
    public int get_etatpartie(){
        return etat_partie;
    }

    /**
     * getter de la partie graphique de la grille
     * @return un gridPane
     */
    public GridPane get_grillegraphique(){
        return this.grille_graphique;
    }
    

    /**
     * Méthode appelé par les handlers de undo et redo pour pop un coup le joué un coup et le mettre dans la pile correcte
     * @param a_pop la pile qui possède le coup à joué, c'est elle qui sera pop
     * @param a_push la pile qui recevra le nouveau coup, c'est elle qui sera push
     * @param nb_clique le nombre de clique à faire pour revenir au coup (si c'est un coup précédent alors 2 sinon 1)
     */
    private void coup(Pile a_pop, Pile a_push, int nb_clique){
        
        Coup coup_pris = new Coup(-1,-1);

        coup_pris=a_pop.pop();

        if(coup_pris.get_x()!=-1)
        {
            for(int i=0;i<nb_clique;i++)
                grille.get(coup_pris.get_x()).get(coup_pris.get_y()).action_clic(true);
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
            coup(redoStack, undoStack,1);
        }
    };

    public Pile getUndo()
    {
        return undoStack;
    }
}