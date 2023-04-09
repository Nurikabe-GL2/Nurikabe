package io.github.nurikabe.niveaux;

import io.github.nurikabe.*;
import io.github.nurikabe.cases.Case;
import io.github.nurikabe.cases.CaseGraphique;
import io.github.nurikabe.cases.CaseNombre;
import io.github.nurikabe.cases.CaseNormale;
import io.github.nurikabe.controller.NiveauController;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.Serializable;


/**
 * Classe Niveau pour représenter un niveau
 */
public class Niveau implements Serializable {
    private final NiveauController controller;

    private final MetadonneesSauvegarde metadonneesSauvegarde;

    private final Label timerLabel;

    /**
     * Variable d'instance grille qui représente le contenu de la grille sous forme d'une ArrayList
     */
    private Grille<Case> grille;

    /**
     * Variable d'instance grilleGraphique qui représente la grille graphique
     */
    private Grille<CaseGraphique> grilleGraphique;

    /**
     * Variable d'instance grilleSolution représentant la solution de la grille
     */
    private final Grille<String> grilleSolution;

    /**
     * Variable d'instance panneauGrille représentant le panneau de la grille graphique
     */
    private final GridPane gridPane;

    /**
     * Variable d'instance pileUndo représentant la pile servant au bouton Undo
     */
    private Pile pileUndo;

    /**
     * Variable d'instance pileRedo représentant la pile servant au bouton Redo
     */
    private Pile pileRedo;

    private Hypothese hypo;

    private boolean estEnModeHypothese = false;
    private Chronometre chrono;

    private final Label scoreLabel;

    private Score score;

    /**
     * Constructeur de la classe Niveau
     */
    public Niveau(MetadonneesSauvegarde metadonneesSauvegarde, NiveauController controller, GridPane gridPane, Label timerLabel, Label scoreLabel) throws Exception {
        this.metadonneesSauvegarde = metadonneesSauvegarde;
        this.controller = controller;
        this.gridPane = gridPane;
        this.timerLabel = timerLabel;
        this.scoreLabel = scoreLabel;

        this.grilleSolution = metadonneesSauvegarde.getSolution().getGrille();
    }

    /*
     * Méthode servant à initialiser la partie.
     * C'est ici qu'on crée les piles pour les boutons undo/redo, qu'on charge le niveau
     * et sa sauvegarde s'il en existe une (la grille du niveau existante, les piles undo/redo etc...)
     * on charge le chronomètre et le score (qui seront affichés si nous sommes en mode ContreLaMontre)
     */
    public void initialiser() throws Exception {
        this.gridPane.getChildren().clear();
        this.pileUndo = new Pile();
        this.pileRedo = new Pile();
        gridPane.getStylesheets().add("/css/Plateau.css");
        chargerGrille();
        if (score == null) score = new Score(1500);
        if (chrono == null) chrono = new Chronometre();
        afficherScore();
        hypo = new Hypothese(this);
        controller.rafraichir();
    }

    /*
     * Récuperer la grille contenant la solution du niveau
     */
    public Grille<String> getGrilleSolution() {
        return grilleSolution;
    }

    /**
     * Méthode chargerGrille qui s'occupe de charger la grille.
     * On commence par charger la solution du niveau, puis on tente de charger sa sauvegarde
     * s'il n'en existe pas on le charge directement en mettant les cases à 0 (vides)
     */
    public void chargerGrille() throws Exception {
        if (!chargerSauvegarde()) {
            //Pas de sauvegarde, création de la grille
            grille = new Grille<>(grilleSolution.getLargeur(), grilleSolution.getHauteur());

            for (int y = 0; y < grille.getHauteur(); y++) {
                for (int x = 0; x < grille.getLargeur(); x++) {
                    final Case uneCase;
                    if (grilleSolution.recup(x, y).equals("b") || grilleSolution.recup(x, y).equals("n")) {
                        uneCase = new CaseNormale(x, y, 0);
                    } else {
                        uneCase = new CaseNombre(x, y, Integer.parseInt(grilleSolution.recup(x, y)));
                    }
                    grille.mettre(x, y, uneCase);
                }
            }
        }

        //Création de la grille graphique
        grilleGraphique = new Grille<>(grilleSolution.getLargeur(), grilleSolution.getHauteur());
        for (int y = 0; y < grilleGraphique.getHauteur(); y++) {
            for (int x = 0; x < grilleGraphique.getLargeur(); x++) {
                grilleGraphique.mettre(x, y, new CaseGraphique(x, y, this));
                GridPane.setRowIndex(grilleGraphique.recup(x, y).getStackPane(), y);
                GridPane.setColumnIndex(grilleGraphique.recup(x, y).getStackPane(), x);
                gridPane.getChildren().addAll(grilleGraphique.recup(x, y).getStackPane());
            }
        }
    }

    /*
     * méthode servant à sauvegarder un niveau en faisant appel à la classe Sauvegarde
     * on sérialise les objets tels que la grille, les piles undo/redo, le chronomètre et le score
     */
    public void sauvegarderNiveau() {
        try {
            metadonneesSauvegarde.ecrireSauvegarde(new Sauvegarde(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode appelée lors de l'affichage du score (même principe que pour le chronomètre)
     */
    public void afficherScore() {
        if (scoreLabel != null) scoreLabel.setText("Score: " + score.getScore());
    }

    /**
     * Méthode appelée lors de l'utilisation du bouton aide
     * on sauvegarde le niveau et on retire 100 points au score (pénalité pour l'utilisation de l'aide)
     */
    public void utilisationAide() {
        if (score.getScore() > 0) score.retirerScore(100);
        afficherScore();
        sauvegarderNiveau();
    }

    /**
     * @return {@code true} si la sauvegarde a été chargée
     */
    public boolean chargerSauvegarde() throws Exception {
        if (!metadonneesSauvegarde.contiensSauvegarde()) return false;

        final Sauvegarde sauvegarde = metadonneesSauvegarde.chargerSauvegarde();
        grille = sauvegarde.recupGrille();
        pileUndo = sauvegarde.recupPileUndo();
        pileRedo = sauvegarde.recupPileRedo();
        chrono = sauvegarde.recupChrono();
        score = sauvegarde.getScore();

        return true;
    }

    /**
     * Méthode victoire qui teste si la grille est terminée
     * si le niveau est complété, on écrit dans la sauvegarde le flag "NIVEAU_COMPLETE"
     * Comme ça lors du chargement des niveaux sur l'interface cela indiquera les niveaux déjà complétés
     */
    public void victoire() {
        final int erreurs = verifier();
        if (erreurs == 0) {
            metadonneesSauvegarde.marquerComplet();

            new Alert(Alert.AlertType.INFORMATION, "Vous avez gagné !").showAndWait();
            controller.ecranPrecedent();
        }
    }

    /**
     * Récupère uen case à partir de ses coordonnées
     *
     * @param x abscisse de la case
     * @param y ordonnée de la case
     *
     * @return la case avec les coordonnées entrées
     */
    public Case recupCase(int x, int y) {
        return grille.recup(x, y);
    }

    /**
     * Renvoie l'état de la case
     *
     * @param x la coordonnée x de la case
     * @param y la coordonnée y de la case
     *
     * @return l'état de la case sous forme de chaine de caractère
     */
    public String etatCase(int x, int y) {
        return grille.recup(x, y).getContenuCase();
    }

    /**
     * Méthode qui renvoie la largeur de la grille
     *
     * @return la largeur de la grille
     */
    public int getLargeur() {
        return grilleSolution.getLargeur();
    }

    /**
     * Méthode qui renvoie la hauteur de la grille
     *
     * @return la hauteur de la grille
     */
    public int getHauteur() {
        return grilleSolution.getHauteur();
    }

    /**
     * Méthode qui renvoie la grille graphique (grille contenant la GridPane du jeu)
     *
     * @return l'état de la partie sous forme d'entier
     */
    public Grille<CaseGraphique> getGrilleGraphique() {
        return grilleGraphique;
    }

    public Grille<Case> getGrille() {
        return grille;
    }

    public void undo() {
        coup(pileUndo, pileRedo, 2);
        controller.rafraichir();
    }

    public void redo() {
        coup(pileRedo, pileUndo, 1);
        controller.rafraichir();
    }
    /**
     * mettre à jour la grillle graphique
     */
    public void majGrilles(){
        for (int y = 0; y < grilleGraphique.getHauteur(); y++) {
            for (int x = 0; x < grilleGraphique.getLargeur(); x++) {
                grilleGraphique.recup(x, y).mettreAJour();
            }
        }
    }

    public boolean estEnModeHypothese(){
        return estEnModeHypothese;
    }

    public void desactiverModeHypothese(){
        estEnModeHypothese=false;
    }

    public void activerModeHypothese(){
        estEnModeHypothese = true;
    }

    public void actionHypothese() {
        hypo.incrementerActions();
    }

    public void confirmerHypothese() {
        hypo.confirmer();
    }

    public void annulerHypothese() {
        hypo.annuler();
    }

    /**
     * Méthode coup appelée par les handlers de Undo et Redo pour pop un coup le joué et le mettre dans la pile correcte
     *
     * @param aPop    la pile qui possède le coup à jouer, c'est elle qui sera pop
     * @param aPush   la pile qui recevra le nouveau coup, c'est elle qui sera push
     * @param nbClics le nombre de clics à faire pour revenir au coup (si c'est un coup précédent alors 2 sinon 1)
     */
    private void coup(Pile aPop, Pile aPush, int nbClics) {
        Coup coupPris = aPop.depiler();
        if (coupPris.x() != -1) {
            for (int i = 0; i < nbClics; i++)
                grilleGraphique.recup(coupPris.x(), coupPris.y()).actionClic();
        }
        aPush.empiler(coupPris);
    }

    /**
     * Getter du stack Undo
     *
     * @return une pile
     */
    public Pile recupUndo() {
        return pileUndo;
    }

    /**
     * Calcule le nombre d'erreurs et le renvoie
     *
     * @return le nombre d'erreurs
     */
    public int verifier() {
        int erreurs = 0;
        for (int x = 0; x < grille.getLargeur(); x++) {
            for (int y = 0; y < grille.getHauteur(); y++) {
                String contenuGrille = grille.recup(x, y).getContenuCase();
                if (contenuGrille.equals(".")) contenuGrille = "b";

                final String contenuSolution = grilleSolution.recup(x, y);
                if (!contenuGrille.equals(contenuSolution)) {
                    erreurs++;
                }
            }
        }

        return erreurs;
    }

    /**
     * Méthode servant à réinitialiser le niveau courant
     * on réinitialise le chronomètre en faisant appel à sa méthode resetAll (on fait de même pour le score)
     * on supprime la sauvegarde du niveau actuel si elle existait
     */
    public void reset() {
        chrono.resetAll();
        score.resetAll();
        try {
            metadonneesSauvegarde.supprimerSauvegarde();
            initialiser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pile recupRedo() {
        return pileRedo;
    }

    public Chronometre getChrono() {
        return chrono;
    }

    public Score getScore() {
        return score;
    }

    public NiveauController getController() {
        return controller;
    }
}