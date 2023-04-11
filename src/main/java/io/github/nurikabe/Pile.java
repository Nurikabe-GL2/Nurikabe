package io.github.nurikabe;

import java.io.Serializable;
import java.util.Stack;

/**
 * Classe Pile qui permet de manipuler les coups joués
 */
public class Pile implements Serializable {
    private final Stack<Coup> pile = new Stack<>();

    /**
     * Méthode empiler permettant d'empiler un coup joué dans la pile locale
     *
     * @param coup le coup à empiler
     */
    public void empiler(Coup coup) {
        this.pile.push(coup);
    }

    /**
     * Méthode depiler permettant de dépiler un coup joué dans la pile locale
     *
     * @return le coup dépilé
     */
    public Coup depiler() {
        return this.pile.pop();
    }

    public boolean estVide() {
        return pile.isEmpty();
    }

    public void vider() {
        pile.clear();
    }

    public int taille() {
        return pile.size();
    }
}