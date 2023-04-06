package io.github.nurikabe;

import io.github.nurikabe.utils.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Parametres implements Serializable {
    private static final Path CHEMIN_PARAMETRES = Path.of("sauvegarde", "parametres.ser");
    private static Parametres instance;

    private boolean remplirCases = false;
    private boolean numeroteChemin = true;
    private boolean afficheErreurs = true;
    private boolean completeTaille1 = false;
    private boolean completeCaseAdj = false;

    public boolean getRemplirCases() {
        return this.remplirCases;
    }

    public boolean getNumeroteChemin() {
        return this.numeroteChemin;
    }

    public boolean getAfficheErreurs() {
        return this.afficheErreurs;
    }

    public boolean getCompleteTaille1() {
        return this.completeTaille1;
    }

    public boolean getCompleteCaseAdj() {
        return this.completeCaseAdj;
    }

    public void setRemplirCases(boolean remplirCases) {
        this.remplirCases = remplirCases;
        sauvegarder();
    }

    public void setNumeroteChemin(boolean numeroteChemin) {
        this.numeroteChemin = numeroteChemin;
        sauvegarder();
    }

    public void setAfficheErreurs(boolean afficheErreurs) {
        this.afficheErreurs = afficheErreurs;
        sauvegarder();
    }

    public void setCompleteTaille1(boolean completeTaille1) {
        this.completeTaille1 = completeTaille1;
        sauvegarder();
    }

    public void setCompleteCaseAdj(boolean completeCaseAdj) {
        this.completeCaseAdj = completeCaseAdj;
        sauvegarder();
    }

    public void sauvegarder() {
        try {
            Files.createDirectories(CHEMIN_PARAMETRES.getParent()); //Création du dossier s'il n'existe pas
            try (ObjectOutputStream stream = new ObjectOutputStream(IOUtils.newBufferedOutputStream(CHEMIN_PARAMETRES))) {
                stream.writeObject(this);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Parametres getParametres() {
        if (instance == null) {
            if (Files.exists(CHEMIN_PARAMETRES)) {
                try (ObjectInputStream stream = new ObjectInputStream(IOUtils.newBufferedInputStream(CHEMIN_PARAMETRES))) {
                    instance = (Parametres) stream.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                instance = new Parametres();
            }
        }

        return instance;
    }

    @Override
    public String toString() {
        return "Objet parametres -> \n Remplissage: " + getRemplirCases() +
                "\n Numerotage des chemins: " + getNumeroteChemin() +
                "\n Affichage des erreurs: " + getAfficheErreurs() +
                "\n Completer les iles de taille 1: " + getCompleteTaille1() +
                "\n Completion cases adjacentes: " + getCompleteCaseAdj();
    }
}
