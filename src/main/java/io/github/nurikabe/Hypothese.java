/**
 * Fichier Niveau.java représentant les niveaux
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX

import io.github.nurikabe.controller.SelectionNiveauxController;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.util.Scanner;


/**
 * Classe Niveau pour représenter un niveau
 */
public class Hypothese {

    private int nombre_Undo_toDo=0;

    Niveau niv;
    
    public Hypothese(Niveau niv){
        this.niv=niv;
    } 

    public boolean recupEtatPartie(){
        return niv.recupEtatPartie();
    }

    /**
     * confirmer l'hypothèse en cours
     */
    public void confirmer(){
        niv.arreterModeHypothese();
    }

    public void incrementer_actions(){
        nombre_Undo_toDo++;
    }

    /**
     * annuler l'hypothèse en cours
     */
    public void annuler(){
        for(int i=0;i<nombre_Undo_toDo;i++){
            niv.undo();
        }
        niv.arreterModeHypothese();
    }
}