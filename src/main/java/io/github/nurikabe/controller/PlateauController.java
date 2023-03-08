package io.github.nurikabe.controller;

import io.github.nurikabe.Niveau;
//import io.github.nurikabe.Logging;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
//import org.slf4j.Logger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import io.github.nurikabe.Chronometre;


/**
 * La classe public implémentant le controller du plateau héritant de VBox qui est la racine du menu principal
 */
public class PlateauController extends VBox {

    /**
     * initialisation du logger pour générer des messages durant l'éxécution suite à des évènements.
     */
    //private static final Logger LOGGER = Logging.getLogger();
    
    /**
     * Variable d'instance privé qui implémente le stage courant
     */
    Stage stage;
    
    /**
     * Variable d'instance représentant la grille qui sera chargé
     */
    Niveau niveau;

    /**
     * Variable d'instance représentant le groupe d'objet qui sera chargé
     */
    Group g = new Group();

    /**
     * instanciation du chronometre
     */
    Chronometre chrono = new Chronometre();


    /**
     * Le constructeur de la classe PlateauController
     * @param s le stage courant
     */
    public PlateauController(Stage s){
            stage=s;
            niveau=new Niveau("src/main/resources/niveaux/moyen_10.txt");


            chrono.debut();


        //Création des boutons undo redo
        Button undo = new Button("undo");
        Button redo = new Button("redo");

        //ajout des handlers
        undo.setOnMouseClicked(niveau.handlerUndo);
        redo.setOnMouseClicked(niveau.handlerRedo);

        HBox horiBox = new HBox();
        VBox vertiBox = new VBox();
        //ajout dans la fenêtre
        horiBox.getChildren().add(undo);
        horiBox.getChildren().add(redo);
   
        vertiBox.getChildren().add(horiBox);

            //ajout dans le groupe de la grille qui sera chargé par la scène
            
            g.getChildren().add(niveau.get_grillegraphique());
            vertiBox.getChildren().add(g);
            //création de la scène
            Scene scene = new Scene(vertiBox, niveau.get_hauteur()*50, niveau.get_largeur()*51.5);
           
           // Window window = scene.getWindow();
           // window.setX(300);
           // window.setY(0);
            
           
            stage.setTitle("Grille Nurikabe");
            stage.setScene(scene);
            stage.show();
            
            /**
             * initialisation d'un gestionnair d'évènement qui gérera la souris
             */
            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
                @Override 
                public void handle(MouseEvent e) { 
                   System.out.println("Hello World"); 
                } 
             };


         // fin du chrono
        chrono.fin();
        long tempsEcoule = chrono.getTempsEcoule();
        System.out.println("Temps écoulé : " + tempsEcoule + " ms");

    }

    /**
     * Méthode public qui renvoie un entier en fonction de l'état de la partie
     * @return l'état de la partie
     */
    public int victoire_partie(){
        return niveau.get_etatpartie();
    }
}