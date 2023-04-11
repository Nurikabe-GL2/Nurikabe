package io.github.nurikabe;

import io.github.nurikabe.utils.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Singleton contenant les paramètres du Nurikabe
 */
public class Parametres implements Serializable {
    private static final Path CHEMIN_PARAMETRES = Path.of("sauvegarde", "parametres.ser");
    private static Parametres instance;

    private boolean remplirCasesNoires = false;
    private boolean numeroterChemin = true;
    private boolean afficherErreurs = true;
    private boolean completerIleDeUn = false;
    private boolean completerCasesAdjacentes = false;

    public boolean doitRemplirCasesNoires() {
        return this.remplirCasesNoires;
    }

    public boolean doitNumeroterChemin() {
        return this.numeroterChemin;
    }

    public boolean doitAfficherErreurs() {
        return this.afficherErreurs;
    }

    public boolean doitCompleterIleDeUn() {
        return this.completerIleDeUn;
    }

    public boolean doitCompleterCasesAdjacentes() {
        return this.completerCasesAdjacentes;
    }

    public void setRemplirCasesNoires(boolean remplirCasesNoires) {
        this.remplirCasesNoires = remplirCasesNoires;
        sauvegarder();
    }

    public void setNumeroterChemin(boolean numeroterChemin) {
        this.numeroterChemin = numeroterChemin;
        sauvegarder();
    }

    public void setAfficherErreurs(boolean afficherErreurs) {
        this.afficherErreurs = afficherErreurs;
        sauvegarder();
    }

    public void setCompleterIleDeUn(boolean completerIleDeUn) {
        this.completerIleDeUn = completerIleDeUn;
        sauvegarder();
    }

    public void setCompleterCasesAdjacentes(boolean completerCasesAdjacentes) {
        this.completerCasesAdjacentes = completerCasesAdjacentes;
        sauvegarder();
    }

    private void sauvegarder() {
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
        return "Objet parametres -> \n Remplissage: " + doitRemplirCasesNoires() +
                "\n Numerotage des chemins: " + doitNumeroterChemin() +
                "\n Affichage des erreurs: " + doitAfficherErreurs() +
                "\n Completer les iles de taille 1: " + doitCompleterIleDeUn() +
                "\n Completion cases adjacentes: " + doitCompleterCasesAdjacentes();
    }
}
