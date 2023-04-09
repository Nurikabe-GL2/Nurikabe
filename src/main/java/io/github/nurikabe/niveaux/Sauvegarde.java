package io.github.nurikabe.niveaux;

import io.github.nurikabe.*;
import io.github.nurikabe.cases.Case;

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
    private final Hypothese hypothese;

    public Sauvegarde(Niveau niveau) {
        this.pileUndo = niveau.recupUndo();
        this.pileRedo = niveau.recupRedo();
        this.grille = niveau.getGrille();
        this.chrono = niveau.getChrono();
        this.score = niveau.getScore();
        this.hypothese = niveau.getHypothese();
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

    public Hypothese getHypothese() {
        return hypothese;
    }
}