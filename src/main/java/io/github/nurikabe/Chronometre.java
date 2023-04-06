package io.github.nurikabe;

import java.io.Serializable;
import java.time.Duration;

/**
 * Classe Chronometre contenant les données du temps d'une partie en cours.
 * <br>Celui-ci ne sera affiché que lors d'une partie en mode contre-la-montre
 */
public class Chronometre implements Serializable {

    /**
     * Debut et fin : représentent le temps (long) avec la méthode currentTimeMillis() de la classe System
     * ensuite les autres variables servent à convertir le temps en seconde et minutes puis à l'afficher
     * avec des chaines de caractères
     */
    private long debut;
    private long fin;
    private int tempsSupp = 0;

    public Chronometre() {
        debut();
        fin();
    }

    /*
     * Méthode appelée pour relancer le chronomètre courant à zero
     */
    public void reset() {
        debut();
        fin();
    }

    /*
     * méthode appelée lors de la réinitialisation d'un niveau
     */
    public void resetAll() {
        debut();
        fin();
        tempsSupp = 0;
    }

    /*
     * méthode appelée pour sauvegarder un niveau (on garde le temps écoulé dans une variable)
     */
    public void sauvegarder() {
        tempsSupp = getTempsEcoule();
        reset();
    }

    /*
     * méthode pour actualiser la variable début
     */
    public void debut() {
        debut = System.currentTimeMillis();
    }

    /*
     * méthode pour actualiser la variable fin
     */
    public void fin() {
        fin = System.currentTimeMillis();
    }

    /*
     * méthode pour récupérer le temps écoulé (debut-fin) + le temps écoulé sauvegardé
     */
    private int getTempsEcoule() {
        fin();
        return (int) (fin - debut) + tempsSupp;
    }

    /**
     * Redéfinition de la méthode toString pour afficher le chronomètre
     */
    @Override
    public String toString() {
        final Duration duration = Duration.ofMillis(getTempsEcoule());
        // https://stackoverflow.com/a/275715
        return "%02d:%02d:%02d".formatted(duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
    }
}