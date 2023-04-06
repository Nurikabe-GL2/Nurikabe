package io.github.nurikabe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Classes représentant l'état de la sauvegarde pour un niveau et un mode de jeu donné.
 */
public class MetadonneesSauvegarde {
    private static final String NIVEAU_COMPLETE = "NIVEAU_COMPLETE";

    private final FichierSolution solution;
    private final ModeDeJeu modeDeJeu;

    private final Path cheminSauvegarde;

    public MetadonneesSauvegarde(FichierSolution solution, ModeDeJeu modeDeJeu) {
        this.solution = solution;
        this.modeDeJeu = modeDeJeu;

        this.cheminSauvegarde = Path.of("sauvegarde", modeDeJeu.getNomMode(), IOUtils.replaceExtension(solution.getCheminNiveau(), "bin").getFileName().toString());
    }

    public FichierSolution getSolution() {
        return solution;
    }

    public ModeDeJeu getModeDeJeu() {
        return modeDeJeu;
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
        try (ObjectInputStream stream = new ObjectInputStream(IOUtils.newBufferedInputStream(cheminSauvegarde))) {
            return (Sauvegarde) stream.readObject();
        } catch (ClassNotFoundException e) { //Impossible
            throw new RuntimeException(e);
        }
    }

    public void ecrireSauvegarde(Sauvegarde sauvegarde) throws IOException {
        Files.createDirectories(cheminSauvegarde.getParent()); //Création du dossier s'il n'existe pas
        try (ObjectOutputStream stream = new ObjectOutputStream(IOUtils.newBufferedOutputStream(cheminSauvegarde))) {
            stream.writeObject(sauvegarde);
        }
    }

    /**
     * Supprime la sauvegarde, si elle existe.
     */
    public void supprimerSauvegarde() {
        try {
            Files.deleteIfExists(cheminSauvegarde);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Marque le niveau comme ayant été complété.
     *
     * @see #estComplete()
     */
    public void marquerComplet() {
        try {
            Files.createDirectories(cheminSauvegarde.getParent()); //Création du dossier s'il n'existe pas
            Files.writeString(cheminSauvegarde, NIVEAU_COMPLETE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Détermine si le niveau a été complété.
     * <br>Cette méthode renvoie toujours un résultat à jour.
     *
     * @see #marquerComplet()
     */
    public boolean estComplete() {
        try {
            if (Files.notExists(cheminSauvegarde))
                return false;

            //Attention à ne pas lire des fichiers binaires sous forme de String
            return Arrays.equals(Files.readAllBytes(cheminSauvegarde), NIVEAU_COMPLETE.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) { //Il y a vraiment un problème si cela arrive
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Détermine si la sauvegarde existe, donc, si le niveau a été entamé.
     */
    public boolean existe() {
        return Files.exists(cheminSauvegarde);
    }
}
