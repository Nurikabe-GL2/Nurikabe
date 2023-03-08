package io.github.nurikabe;

/**
 * faire appele à la class
 *
 * Chronometre chrono = new Chronometre();
 * chrono.debut();
 * // lancer la partie
 * chrono.fin();
 * long tempsEcoule = chrono.getTempsEcoule();
 * System.out.println("Temps écoulé : " + tempsEcoule + " ms");
 */
public class Chronometre {
    private long debut;
    private long fin;

    public void debut() {
        debut = System.currentTimeMillis();
    }

    public void fin() {
        fin = System.currentTimeMillis();
    }

    public long getTempsEcoule() {
        return fin - debut;
    }
}