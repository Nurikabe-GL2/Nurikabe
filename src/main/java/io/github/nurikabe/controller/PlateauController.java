package io.github.nurikabe.controller;

import io.github.nurikabe.Grille;
import io.github.nurikabe.Case;
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


// extends VBox car c'est la racine du menu principal
public class PlateauController extends VBox {

    private static final Logger LOGGER = Logging.getLogger();
    Stage stage;
    Grille niveau;
    Group g = new Group();

    public PlateauController(Stage s){
            stage=s;
            GridPane grid = new GridPane();
            //System.out.println("Working Directory = " + System.getProperty("user.dir"));
            niveau=new Grille("src/main/resources/niveaux/moyen_10.txt");
            System.out.println(niveau.get_largeur());
            for(int i=0;i<niveau.get_largeur();i++){
                for(int j=0;j<niveau.get_hauteur();j++){
                     
                    Case une_case= new Case(niveau.etat_case(i,j), i, j, 50, 50, niveau, stage);
                    /*  
                      Button button = new Button();
                      button.setPrefSize(50, 50);
                      button.setOnAction(new EventHandler<ActionEvent>()
                        {            
                            @Override
                            public void handle(ActionEvent event)
                            {
                                if(button.getGraphic()==null)button.setGraphic(create_image("img/noir1.png", 35, 35));
                                else button.setGraphic(null);
                                System.out.println("clicked\n");
                            }

                        }); 
                    */
                    
                    //System.out.println("classe : "+une_case.get_button().getClass());
                    GridPane.setRowIndex(une_case.get_button(), i);
                    GridPane.setColumnIndex(une_case.get_button(), j);
                    grid.getChildren().addAll(une_case.get_button());
                }
            }

            g.getChildren().add(grid);
            
            Scene scene = new Scene(g, 500, 500);

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

    public static ImageView create_image(String name, int x, int y){
            Image image = new Image(name);
            ImageView view = new ImageView(image);
            view.setPreserveRatio(false);
            view.setFitWidth(x);
            view.setFitHeight(y);
            return view;
    }  

    public int victoire_partie(){
        return niveau.get_etatpartie();
    } 
    
}
