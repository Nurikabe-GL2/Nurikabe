package io.github.nurikabe;

import io.github.nurikabe.niveaux.Niveau;

import java.io.Serializable;

/**
 * Classe Niveau pour représenter un niveau
 */
public class Hypothese implements Serializable {
    private boolean actif = false;

    /**
     * Nombre d'undo à faire si hypothèse annulée
     */
    private int nombreUndoToDo = 0;

    public boolean estActif() {
        return actif;
    }

    public void nouvelleHypothese() {
        actif = true;
    }

    /**
     * Confirme l'hypothèse et met à jour la grille.
     */
    public void confirmer() {
        actif = false;
        razActions();
    }

    /**
     * Augmente le nombre d'undo à effectuer en cas d'annulation.
     */
    public void incrementerActions() {
        nombreUndoToDo++;
    }

    /**
     * Annule l'hypothèse en cours en effectuant le nombre d'undo enregistré.
     */
    public void annuler(Niveau niv) {
        actif = false;
        for (int i = 0; i < nombreUndoToDo; i++) niv.undo();
        razActions();
    }

    /**
     * Remet à zero le nombre d'undo à effectuer.
     */
    private void razActions() {
        nombreUndoToDo = 0;
    }
}