package io.github.nurikabe;

import java.io.*;
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
    private long debut = System.currentTimeMillis();

    /*
     * méthode appelée lors de la réinitialisation d'un niveau
     */
    public void resetAll() {
        debut = System.currentTimeMillis();
    }

    /*
     * méthode pour récupérer le temps écoulé (debut-fin) + le temps écoulé sauvegardé
     */
    private int getTempsEcoule() {
        return (int) (System.currentTimeMillis() - debut);
    }

    @Serial
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeInt(getTempsEcoule());
    }

    @Serial
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        debut = System.currentTimeMillis() - stream.readInt();
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