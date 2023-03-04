package io.github.nurikabe.controller;

import io.github.nurikabe.*;
import java.io.*;
import io.github.nurikabe.Logging;
import io.github.nurikabe.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import java.util.ArrayList;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.shape.Line; 
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File; 
import java.net.URL;
import javafx.stage.Window;


// extends VBox car c'est la racine du menu principal
public class PlateauController extends VBox {

    private static final Logger LOGGER = Logging.getLogger();
    Stage stage;
    Grille niveau;
    Group g = new Group();

    public PlateauController(Stage s){
            stage=s;
            niveau=new Grille("src/main/resources/niveaux/moyen_10.txt");

            g.getChildren().add(niveau.get_grillegraphique());
            
            Scene scene = new Scene(g, niveau.get_hauteur()*50, niveau.get_largeur()*50);
           
           // Window window = scene.getWindow();
           // window.setX(300);
           // window.setY(0);
            
            stage.setTitle("Grille Nurikabe");
            stage.setScene(scene);
            stage.show();
            
            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
                @Override 
                public void handle(MouseEvent e) { 
                   System.out.println("Hello World"); 
                } 
             };   

    }

    public int victoire_partie(){
        return niveau.get_etatpartie();
    } 
    
}
