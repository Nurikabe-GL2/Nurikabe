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
import org.slf4j.Logger;
import java.util.ArrayList;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.shape.Line; 
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File; 
import java.net.URL;
import javafx.scene.layout.*;


/**
 * Classe abstraite représentant une case
 */
public abstract class Case {

    /**
     * coordonnée x de la case
     */
    int x;

    /**
     * coordonnée y de la case
     */
    int y;

    /**
     * le type de la case
     */
    int type;

    //new Button();

    /**
     * Le construteur de la classe Case
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     */
    public Case(int x, int y, int type){
      this.x=x;
      this.y=y;
      this.type=type;
    } 

    /**
     *  prototype de la méthode qui défini la réaction de la case quand on clique dessus 
     */
    public abstract void action_clic();
    
    /**
     * getter renvoyant le type de la case ici 1 car c'est un nombre
     */
    public int get_type(){
      return type;
    }
    
    /**
     * getter qui renvoie le contenue de la case sous forme de chaine de caractère
     * @return le contenue de la case
     */
    public abstract String get_case();
    
    /**
     * getter qui renvoie le pane de la case
     * @return le pane de la case
     */
    public abstract StackPane get_pane();
}