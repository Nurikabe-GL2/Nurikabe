package io.github.nurikabe;

import java.io.*;

/**
 * La classe représentant un coup joué servant pour undo redo
 */
public class Coup implements Serializable{
    /**
     * Variable d'instance représentant la coordonnée x d'un coup joué
     */
    int x;

    /**
     * Variable d'instance représentant la coordonnée y d'un coup joué
     */
    int y;

    /**
     * Le constructeur de la classe coup
     * @param x
     * @param y
     */
    public Coup(int x, int y)
    {
        this.x=x;
        this.y=y;
    }

    /**
     * getter de la variable x d'un coup
     * @return la coordonnée x d'un coup
     */
    int get_x()
    {
        return x;
    }

    /**
     * getter de la variable y d'un coup
     * @return la coordonnée y d'un coup
     */
    int get_y()
    {
        return y;
    }

    /**
     * setter de la variable x d'un coup
     */
    void set_x(int new_x)
    {
        this.x=new_x;
    }

    /**
     * setter de la variable y d'un coup
     */
    void set_y(int new_y)
    {
        this.y=new_y;
    }
}