package io.github.nurikabe;

import java.io.Serializable;


/**
 * classe Score servant à représenter le score du jeu
 */
public class Score implements Serializable {

    /**
     * score : entier initialisé à 1500
     */
    private int score = 1500;

    /**
     * constructeur du score
     *
     * @param score variable pour affecter le score de début
     */
    public Score(int score) {
        this.score = score;
    }

    /**
     * méthode pour réinitialiser le score
     */
    public void reset_all() {
        score = 1500;
    }

    /**
     * retirer un nombre de points au score
     *
     * @param nb nombre de points à retirer
     */
    public void retirerScore(int nb) {
        if (score > 0) score -= nb;
    }

    /**
     * récuperer le score
     *
     * @return le score
     */
    public int getScore() {
        return score;
    }

    /**
     * setter pour le score
     *
     * @param s score à mettre
     */
    public void setScore(int s) {
        score = s;
    }

}