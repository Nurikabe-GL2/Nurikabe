/**
 * Fichier Pile.java représentant une pile pour les coups joués
 */

// Package GitHub
package io.github.nurikabe;

import java.io.Serializable;
import java.util.Stack;

/**
 * Classe Pile qui permet de manipuler les coups joués
 */
public class Pile implements Serializable {
    /**
     * Variable d'instance privée pile qui permet de gérer une pile
     */
    private final Stack<Coup> pile;

    /**
     * Constructeur de la classe Pile
     */
    Pile() {
        pile = new Stack<>();
    }

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
}