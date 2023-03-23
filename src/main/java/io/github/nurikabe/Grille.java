package io.github.nurikabe;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@SuppressWarnings("unchecked")
public class Grille<T> implements Serializable {
    private final Object[] grille;
    private final int largeur;
    private final int hauteur;

    public Grille(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;

        this.grille = new Object[largeur * hauteur];
    }

    @NotNull
    public T get(int x, int y) {
        return (T) grille[getIndex(x, y)];
    }

    public void set(int x, int y, @NotNull T t) {
        grille[getIndex(x, y)] = t;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                builder.append(get(x, y));
            }
            builder.append('\n');
        }

        return builder.toString();
    }

    private int getIndex(int x, int y) {
        return x + y * largeur;
    }
}
