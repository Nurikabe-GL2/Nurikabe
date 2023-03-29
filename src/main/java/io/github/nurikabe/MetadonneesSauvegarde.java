package io.github.nurikabe;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MetadonneesSauvegarde {
    private final GrilleSolution grilleSolution;
    private final ModeDeJeu modeDeJeu;

    private final Path cheminSauvegarde;

    public MetadonneesSauvegarde(GrilleSolution grilleSolution, ModeDeJeu modeDeJeu) {
        this.grilleSolution = grilleSolution;
        this.modeDeJeu = modeDeJeu;

        this.cheminSauvegarde = Path.of("sauvegarde", modeDeJeu.recupNomMode(), grilleSolution.getCheminNiveau().getFileName().toString());
    }

    public boolean contiensSauvegarde() {
        return Files.exists(cheminSauvegarde);
    }

    /**
     * @return L'objet {@link Sauvegarde} contenant l'état de la partie sauvegardée
     *
     * @throws IOException Si la sauvegarde n'existe pas
     * @see #contiensSauvegarde()
     */
    public Sauvegarde chargerSauvegarde() throws IOException {
        try (ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(cheminSauvegarde)))) {
            return (Sauvegarde) stream.readObject();
        } catch (ClassNotFoundException e) { //Impossible
            throw new RuntimeException(e);
        }
    }

    /**
     * Détermine si le niveau a été complété
     * <br>Cette méthode renvoie toujours un résultat à jour.
     */
    public boolean estComplete() {
        try {
            if (Files.notExists(cheminSauvegarde))
                return false;

            return Files.readString(cheminSauvegarde).equals("NIVEAU_COMPLETE");
        } catch (IOException e) { //Il y a vraiment un problème si cela arrive
            throw new UncheckedIOException(e);
        }
    }
}
