package io.github.nurikabe;

//import org.slf4j.Logger;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;


import java.util.Scanner;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 
import javafx.stage.Stage;
import java.io.*;


/**
 * Classe public représentant une grille
 */
public class Sauvegarde implements Serializable{

    Grille<Case> grille;
    
    
    private Pile undoStack;
    

    private Pile redoStack;

    /**
     * Le construteur de la grille
     * @param name le nom de la grille
     */
    public Sauvegarde(){
        this.undoStack=null;
        this.redoStack=null;
        this.grille=null;
    }

    public void setGrille(Grille<Case> grille){
        this.grille=grille;
    }

    public void setUndoPile(Pile undo){
        this.undoStack=undo;
    }

    public void setRedoPile(Pile redo){
        this.redoStack=redo;
    }


    public Pile get_undo_pile(){
        return undoStack;
    }

    public Pile get_redo_pile(){
        return redoStack;
    }

    public Grille<Case> get_grille(){
        return grille;
    }

}