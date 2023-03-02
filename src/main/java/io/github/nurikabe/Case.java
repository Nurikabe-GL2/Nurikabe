package io.github.nurikabe;

import io.github.nurikabe.controller.PlateauController;
import io.github.nurikabe.Grille;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.*;
import java.awt.Color;
import java.awt.GridLayout;
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


public class Case {

    int x, y;
    Button button;
    Stage stage;
    //new Button();
    public Case(String name, int x, int y, int l, int L, Grille niveau, Stage s){
      this.x=x;
      this.y=y;
      this.stage=s;
      if(name.equals("b")||name.equals("n"))button=new Button();
      else button=new Button(name);
      if(name.equals("n"))button.setGraphic(PlateauController.create_image("img/noir1.png", l-15, L-15));
      else if(name.equals("b"))button.setGraphic(null);
      button.setPrefSize(l, L);
            button.setOnAction(new EventHandler<ActionEvent>()
                {            
                  @Override
                    public void handle(ActionEvent event)
                    {
          
                      if(niveau.changer_etat_case(x,y)==1){
                        
                          if(button.getGraphic()==null){
                          
                            button.setGraphic(PlateauController.create_image("img/noir1.png", l-15, L-15));
                         
                          }
                          
                          else {
                           
                            button.setGraphic(null);
                        
                          }
                          
                            System.out.println("clicked\n");
                            niveau.victoire(stage);
                    
                      }
                      niveau.afficher_grille();
                    }

                });
    } 

    public Button get_button(){
      return button;
    }
}