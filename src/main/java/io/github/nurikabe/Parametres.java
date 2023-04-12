package io.github.nurikabe;

import io.github.nurikabe.utils.IOUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Singleton contenant les paramètres du Nurikabe
 * <p>
 * Les propriétés sont les valeurs réelles alors que les getters peuvent retourner des valeurs différentes des propriétés,
 * en fonction d'une autre propriété.
 */
public class Parametres implements Serializable {
    private static final Path CHEMIN_PARAMETRES = Path.of("sauvegarde", "parametres.bin");
    private static Parametres instance;

    private BooleanProperty remplirCasesNoires = new SimpleBooleanProperty(false);
    private BooleanProperty numeroterChemin = new SimpleBooleanProperty(true);
    private BooleanProperty afficherErreurs = new SimpleBooleanProperty(false);
    private BooleanProperty completerIleDeUn = new SimpleBooleanProperty(false);
    private BooleanProperty completerCasesAdjacentes = new SimpleBooleanProperty(false);

    public Parametres() {
        remplirCasesNoires.addListener(x -> sauvegarder());
        numeroterChemin.addListener(x -> sauvegarder());
        afficherErreurs.addListener(x -> sauvegarder());
        completerIleDeUn.addListener(x -> sauvegarder());
        completerCasesAdjacentes.addListener(x -> sauvegarder());
    }

    public BooleanProperty remplirCasesNoiresProperty() {
        return remplirCasesNoires;
    }

    public boolean doitRemplirCasesNoires() {
        return remplirCasesNoiresProperty().get();
    }

    public BooleanProperty numeroterCheminProperty() {
        return numeroterChemin;
    }

    public boolean doitNumeroterChemin() {
        return numeroterCheminProperty().get();
    }

    public BooleanProperty afficherErreursProperty() {
        return afficherErreurs;
    }

    public boolean doitAfficherErreurs() {
        return !doitRemplirCasesNoires() && afficherErreursProperty().get();
    }

    public BooleanProperty completerIleDeUnProperty() {
        return completerIleDeUn;
    }

    public boolean doitCompleterIleDeUn() {
        return !doitRemplirCasesNoires() && completerIleDeUnProperty().get();
    }

    public BooleanProperty completerCasesAdjacentesProperty() {
        return completerCasesAdjacentes;
    }

    public boolean doitCompleterCasesAdjacentes() {
        return !doitRemplirCasesNoires() && completerCasesAdjacentesProperty().get();
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

    @Serial
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeBoolean(remplirCasesNoires.get());
        stream.writeBoolean(numeroterChemin.get());
        stream.writeBoolean(afficherErreurs.get());
        stream.writeBoolean(completerIleDeUn.get());
        stream.writeBoolean(completerCasesAdjacentes.get());
    }

    @Serial
    private void readObject(ObjectInputStream stream) throws IOException {
        remplirCasesNoires = new SimpleBooleanProperty(stream.readBoolean());
        numeroterChemin = new SimpleBooleanProperty(stream.readBoolean());
        afficherErreurs = new SimpleBooleanProperty(stream.readBoolean());
        completerIleDeUn = new SimpleBooleanProperty(stream.readBoolean());
        completerCasesAdjacentes = new SimpleBooleanProperty(stream.readBoolean());
    }
}
