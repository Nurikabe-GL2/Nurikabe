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
    /*nombre de undo à faire si hypothèse annulée */
    private int nombre_Undo_toDo=0;
    /*niveau courant de l'hypothèse */
    Niveau niv;
    
    public Hypothese(Niveau niv){
        this.niv=niv;
    } 
    /**
     * confirmer l'hypothèse en cours on remet les actions à 0, on désactive le mode hypothèse et on met à jour des grilles
     */
    public void confirmer(){
        raz_actions();
        niv.desactiverModeHypothese();
        niv.majGrilles();
    }
    /**
     * incrémenter le nombre d'actions
     */
    public void incrementer_actions(){
        nombre_Undo_toDo++;
    }
    /**
     * remettre à 0 les actions
     */
    public void raz_actions(){
        nombre_Undo_toDo=0;
    }

    /**
     * annuler l'hypothèse en cours
     * effectuer autant de undo qu'il y a d'actions (remettre la grille à son état avant celui du mode hypothèse)
     */
    public void annuler(){
        System.out.println("nombre undo à faire : "+nombre_Undo_toDo);
        niv.desactiverModeHypothese();
       for(int i=0;i<nombre_Undo_toDo;i++){
            niv.undo();
        }
        raz_actions();
        niv.majGrilles();

    }
    /**
     * activer mode hypothèse
     */
    public void activer(){
        niv.activerModeHypothese();
    }
}