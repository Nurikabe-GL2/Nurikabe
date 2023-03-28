package io.github.nurikabe;

import java.io.Serializable;


/**
 * faire appel à la classe
 * <p>
 * Chronometre chrono = new Chronometre();
 * chrono.debut();
 * // lancer la partie
 * chrono.fin();
 * long tempsEcoule = chrono.getTempsEcoule();
 * System.out.println("Temps écoulé : " + tempsEcoule + " ms");
 */
public class Chronometre implements Serializable {

    private long debut;
    private long fin;
    private int minutes = 0, secondes = 0;
    private String minutes_s = "00", secondes_s = "00";

    public Chronometre() {
        debut();
        fin();
    }

    public void reset() {
        debut();
        fin();
    }

    public void debut() {
        debut = System.currentTimeMillis();
    }

    public void fin() {
        fin = System.currentTimeMillis();
    }

    private int getTempsEcoule() {
        fin();
        // System.out.println(fin-debut);
        return (int) (fin - debut);
    }

    private void convertirTempsEcoule() {
        int tempsEcoule = getTempsEcoule();
        if (tempsEcoule >= 60000) {
            minutes = tempsEcoule / 60000;
            tempsEcoule -= minutes * 60000;
        }
        secondes = tempsEcoule / 1000;

        if (secondes < 10) secondes_s = "0" + secondes;
        else secondes_s = secondes + "";

        if (minutes < 10) minutes_s = "0" + minutes;
        else minutes_s = minutes + "";

    }

    @Override
    public String toString() {

        convertirTempsEcoule();
        return minutes_s + ":" + secondes_s;

    }
}