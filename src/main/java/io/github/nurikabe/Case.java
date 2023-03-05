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


public abstract class Case {

    int x, y;
    //new Button();
    public Case(int x, int y){
      this.x=x;
      this.y=y;
    } 
    
    public abstract void action_clic();
    public abstract int get_type();
    public abstract String get_case();
    public abstract StackPane get_pane();
}