package io.github.nurikabe;

import java.io.Serializable;


/**
 * Classe Score servant à représenter le score du jeu
 */
public class Score implements Serializable {
    private int score;

    /**
     * Constructeur du score
     *
     * @param score Le score de début
     */
    public Score(int score) {
        this.score = score;
    }

    /**
     * Réinitialise le score
     */
    public void resetAll() {
        score = 1500;
    }

    /**
     * Retire un nombre de points au score
     *
     * @param nb nombre de points à retirer
     */
    public void retirerScore(int nb) {
        if (score > 0) score -= nb;
    }

    public int getScore() {
        return score;
    }
}