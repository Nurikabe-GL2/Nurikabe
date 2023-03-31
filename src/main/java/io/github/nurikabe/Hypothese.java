/**
 * Fichier Hypothese.java représentant les niveaux
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX

/**
 * Classe Niveau pour représenter un niveau
 */
public class Hypothese {
    /*nombre de undo à faire si hypothèse annulée */
    private int nombreUndoToDo=0;
    /*niveau courant de l'hypothèse */
    private final Niveau niv;
    
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
    public void incrementerActions(){
        nombreUndoToDo++;
    }
    /**
     * remettre à 0 les actions
     */
    public void raz_actions(){
        nombreUndoToDo=0;
    }

    /**
     * annule l'hypothèse en cours
     * effectuer autant de undo qu'il y a d'actions (remettre la grille à son état avant celui du mode hypothèse)
     */
    public void annuler(){
        System.out.println("nombre undo à faire : "+nombreUndoToDo);
        niv.desactiverModeHypothese();
       for(int i=0;i<nombreUndoToDo;i++){
            niv.undo();
        }
        raz_actions();
        niv.majGrilles();

    }
}