package io.github.nurikabe;

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


public class Grille {

    String grille[][];
    String grille_solution[][];
    int largeur, hauteur, etat_partie=0;

    public Grille(String name){
        charger_grille(name);
    }

    public void charger_grille(String name){
      try {
        FileInputStream fichier = new FileInputStream(name);
        Scanner lecture = new Scanner(fichier);
        this.hauteur = lecture.nextInt();
        this.largeur = lecture.nextInt();
         grille_solution=new String[largeur][hauteur];
      grille=new String[largeur][hauteur];
      for (int i = 0; i < largeur; i++) {
         for (int j = 0; j < hauteur; j++) {
            grille_solution[i][j] = lecture.next();
            grille[i][j]=grille_solution[i][j];
            //if(grille[i][j].equals("b")||grille[i][j].equals("n"))grille[i][j]="b";
         }
      }
      }catch (Exception e){
        System.out.println("erreur lors de la lecture de la grille : "+e);
      }
    }

    public void victoire(Stage s){
        int count=0;
          for (int i = 0; i < largeur; i++) {
                for (int j = 0; j < hauteur; j++) {
                    if(grille_solution[i][j] == grille[i][j])count++;
                }
            }
            System.out.println("count : "+count+"\n l*L : "+largeur*hauteur);
      if(count==(largeur*hauteur)-1){
        etat_partie=1;
        System.out.println("PARTIE GAGNEE !!!!");
        s.close();
      }
      else etat_partie=0;
    }
   
    public int changer_etat_case(int x, int y){
        if(grille[x][y].equals("b")==true)grille[x][y]="n";
        
        else if(grille[x][y].equals("n")==true)grille[x][y]="b";
        
        return 1;
    }

    public String etat_case(int x, int y){
        return grille[x][y];
    }

    public int get_largeur(){
        return largeur;
    }

    public int get_hauteur(){
        return hauteur;
    }

    public void afficher_grille(){
         for (int i = 0; i < largeur; i++) {
                for (int j = 0; j < hauteur; j++) {
                    System.out.print(grille[i][j]+" ");
                }
                System.out.println("");
            }
    }

    public int get_etatpartie(){
        return etat_partie;
    }

}