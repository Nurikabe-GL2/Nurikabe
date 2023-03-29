/**
 * Fichier Sauvegarde.java pour sauvegarder la grille de jeu et les piles d'undo-redo
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX

import java.io.Serializable;

/**
 * Classe contenant les variables à sauvegarder d'un {@link Niveau}
 */
public class Sauvegarde implements Serializable {
    private final Grille<Case> grille;
    private final Pile pileUndo;
    private final Chronometre chrono;
    private final Pile pileRedo;
    private final Score score;

    public Sauvegarde() {
        this.pileUndo = null;
        this.pileRedo = null;
        this.grille = null;
        this.chrono = null;
        this.score = null;
    }

    public Sauvegarde(Niveau niveau) {
        this.pileUndo = niveau.recupUndo();
        this.pileRedo = niveau.recupRedo();
        this.grille = niveau.getGrille();
        this.chrono = niveau.getChrono();
        this.score = niveau.getScore();
    }

    public Chronometre recupChrono() {
        chrono.reset();
        return chrono;
    }

    public Score getScore() {
        return score;
    }

    public Grille<Case> recupGrille() {
        return grille;
    }

    public Pile recupPileUndo() {
        return pileUndo;
    }

    public Pile recupPileRedo() {
        return pileRedo;
    }
}