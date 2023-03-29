/**
 * Fichier Niveau.java représentant les niveaux
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX

import io.github.nurikabe.controller.SelectionNiveauxController;
import javafx.stage.Modality;
import javafx.scene.layout.GridPane;

import java.util.Scanner;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;


/**
 * Classe Niveau pour représenter un niveau
 */
public class Niveau implements Serializable {
    public static String pathSauvegarde = "sauvegarde/";
    private final SelectionNiveauxController select;
    /**
     * Variable d'instance nomNiveau qui représente le nom du niveau
     */
    private final String cheminNiveau;
    /**
     * Variable d'instance modeDeJeu qui représente le mode de jeu
     */
    private final String modeJeu;
    private final Label timerLabel;
    /**
     * Variable d'instance grille qui représente le contenu de la grille sous forme d'une ArrayList
     */
    Grille<Case> grille;
    /**
     * Variable d'instance grilleGraphique qui représente la grille graphique
     */
    Grille<CaseGraphique> grilleGraphique;
    /**
     * Variable d'instance grilleSolution représentant la solution de la grille
     */
    Grille<String> grilleSolution;
    /**
     * Variable d'instance panneauGrille représentant le panneau de la grille graphique
     */
    private final GridPane gridPane;
    /**
     * Variable d'instance sauvegarde représentant la sauvegarde de la grille
     */
    Sauvegarde sauvegarde;
    /**
     * Variable d'instance stage qui représente le plateau de jeu
     */
    Stage stage;
    /**
     * Variable d'instance pileUndo représentant la pile servant au bouton Undo
     */
    private Pile pileUndo;
    /**
     * Variable d'instance pileRedo représentant la pile servant au bouton Redo
     */
    private Pile pileRedo;
    private boolean etatPartie = false;
    private Chronometre chrono;

    private Label timerLabel, scoreLabel;

    private Score score;

    public static Grille<String> chargerGrilleSolution(String cheminGrille) throws IOException {
        try (Scanner lecture = new Scanner(new FileInputStream(cheminGrille))) {
            int largeur = lecture.nextInt();
            int hauteur = lecture.nextInt();
            Grille<String> grilleSol = new Grille<>(largeur, hauteur);

            for (int y = 0; y < grilleSol.recupHauteur(); y++) {

                for (int x = 0; x < grilleSol.recupLargeur(); x++) {

                    grilleSol.mettre(x, y, lecture.next());

                }
            }
            return grilleSol;
        }
    }

   /**
    * Constructeur de la classe Niveau
    * @param cheminNiveau le chemin vers la grille
    */
   public Niveau(Stage stage, String cheminNiveau, String mode, SelectionNiveauxController select, Label timer, Label sc) throws Exception{
      this.select=select;
      this.stage=stage;
      this.cheminNiveau = cheminNiveau;
      this.mode_jeu=mode;
      this.timerLabel=timer;
      this.scoreLabel=sc;
      initialiser();
      afficherScore();
   }
    /*
     * Méthode servant à initialiser la partie.
     * C'est ici qu'on crée les piles pour les boutons undo/redo, qu'on charge le niveau
     * et sa sauvegarde s'il en existe une (la grille du niveau existante, les piles undo/redo etc...)
     * on charge le chronomètre et le score (qui seront affiché si nous sommes en mode ContrLaMontre)
     */
    private void initialiser() throws Exception {
        this.sauvegarde = new Sauvegarde();
        this.gridPane.getChildren().clear();
        this.pileUndo = new Pile();
        this.pileRedo = new Pile();
        gridPane.getStylesheets().add("/css/Plateau.css");
        chargerGrille();
        if(score==null)score=new Score(1500);
        if (chrono == null) chrono = new Chronometre();
        majChronometre();
        afficherScore();
    }

    /*
     * Récuperer la grille contenant la solution du niveau
     */
    public Grille<String> getGrilleSolution() {
        return grilleSolution;
    }

    /**
     * Méthode chargerGrille qui s'occupe de charger la grille.
     * on commence par charger la solution du niveau, puis on tente de charger sa sauvegarde
    * si il n'en existe pas on le charge directement en mettant les cases à 0 (vides)
    */
    public void chargerGrille() throws Exception {
        grilleSolution = chargerGrilleSolution(cheminNiveau);

        if (chargerNiveau(cheminNiveau) == 0) {


            grille = new Grille<>(grilleSolution.recupLargeur(), grilleSolution.recupHauteur());
            grilleGraphique = new Grille<>(grilleSolution.recupLargeur(), grilleSolution.recupHauteur());

            for (int y = 0; y < grilleGraphique.recupHauteur(); y++) {

                for (int x = 0; x < grilleGraphique.recupLargeur(); x++) {
                    //Case une_case;

                    if (grilleSolution.recup(x, y).equals("b") || grilleSolution.recup(x, y).equals("n")) {
                        grille.mettre(x, y, new CaseNormale(x, y));
                    } else grille.mettre(x, y, new CaseNombre(x, y, Integer.parseInt(grilleSolution.recup(x, y))));

                    grilleGraphique.mettre(x, y, new CaseGraphique(x, y, 30, 30, this));
                    //System.out.println(grille.get(i).get(j).get_pane()!=null);
                    GridPane.setRowIndex(grilleGraphique.recup(x, y).recupPanneau(), y);
                    GridPane.setColumnIndex(grilleGraphique.recup(x, y).recupPanneau(), x);

                    gridPane.getChildren().addAll(grilleGraphique.recup(x, y).recupPanneau());
                    //lecture.close();
                }
            }
        } else {
            grilleGraphique = new Grille<>(grilleSolution.recupLargeur(), grilleSolution.recupHauteur());

            for (int y = 0; y < grilleGraphique.recupHauteur(); y++) {

                for (int x = 0; x < grilleGraphique.recupLargeur(); x++) {


                    grilleGraphique.mettre(x, y, new CaseGraphique(x, y, 30, 30, this));
                    //System.out.println(grille.get(i).get(j).get_pane()!=null);
                    GridPane.setRowIndex(grilleGraphique.recup(x, y).recupPanneau(), y);
                    GridPane.setColumnIndex(grilleGraphique.recup(x, y).recupPanneau(), x);

                    gridPane.getChildren().addAll(grilleGraphique.recup(x, y).recupPanneau());
                }

            }
        }
    }

    /*
     * méthode servant à sauvegarder un niveau en faisant appel à la classe Sauvegarde
     * on sérialise les objets tels que la grille, les piles undo/redo, le chronomètre et le score
     */
    public void sauvegarderNiveau() {
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try {
            File sauv = new File(Niveau.pathSauvegarde + cheminNiveau.substring(27) + modeJeu);
            //System.out.println(nom_niveau+mode_jeu);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(sauv))) {
                sauvegarde.mettreGrille(grille);
                sauvegarde.setRedoPile(pileRedo);
                sauvegarde.mettrePileUndo(pileUndo);
                chrono.sauvegarder();
                sauvegarde.setChrono(chrono);
                sauvegarde.setScore(score);
                System.out.println("score : "+score);
                oos.writeObject(this.sauvegarde);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
    * méthode pour mettre à jour le chronomètre.
    * si le label pour le chronomètre n'est pas nul, on y met le chrono (appel à la méthode toString())
    */
    public void majChronometre() {
            if (timerLabel != null) timerLabel.setText(chrono.toString());
            System.out.println("moved");
    }

    /**
     * methode appelée lors de l'affichage du score (même principe que pour le chronomètre)
     */
    public void afficherScore(){
        if(scoreLabel!=null)scoreLabel.setText("Score: "+score.getScore());
    }

    /**
     * methode appelée lors de l'utilisation du bouton aide
     * on sauvegarde le niveau et on retire 100 points au score (pénalité pour l'utilisation de l'aide)
     */
    public void utilisation_aide(){
        if(score.getScore()>0)score.retirerScore(100);
        afficherScore();
        sauvegarderNiveau();
    }


    public int chargerNiveau(String nomNiveau) throws Exception {
        File sauv = new File(Niveau.pathSauvegarde + nomNiveau.substring(27) + modeJeu);
        if (sauv.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(sauv))) {
                sauvegarde = (Sauvegarde) ois.readObject();
                grille = sauvegarde.recupGrille();
                pileUndo = sauvegarde.recupPileUndo();
                pileRedo = sauvegarde.recupPileRedo();
                chrono = sauvegarde.recupChrono();
                score = sauvegarde.getScore();
                return 1;
            }
        } else {
            return 0;
        }
    }

    /**
     * Méthode victoire qui teste si la grille est terminée
     * si le niveau est complété, on insert dans la sauvegarde le flag "NIVEAU_COMPLETE"
     * Comme ça lors du chargement des niveaux sur l'interface cela indiquera les niveaux déjà complétés
     */
    public void victoire() {

        majChronometre();

        final int erreurs = verifier();

        if (erreurs == 0) {
            try {
                File myFile = new File(Niveau.pathSauvegarde + cheminNiveau.substring(27) + modeJeu);
                myFile.delete();

                FileWriter sauv = new FileWriter(Niveau.pathSauvegarde + cheminNiveau.substring(27) + modeJeu);
                sauv.write("NIVEAU_COMPLETE");
                sauv.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            etatPartie = true;



            System.out.println("PARTIE GAGNEE !!!!");
            select.refreshLevels();
        }
    }

    /**
     * récupérer uen case à partir de ses coordonnées
     * @param x abscisse de la case
     * @param y  ordonnée de la case
     * @return la case avec les coordonnées entrées
     */
    public Case recupCase(int x, int y) {
        return grille.recup(x, y);
    }

    /**
     * Méthode etatCase qui renvoie l'état de la case
     *
     * @param x la coordonné x de la case
     * @param y la coordonné y de la case
     *
     * @return l'état de la case sous forme de chaine de caractère
     */
    public String etatCase(int x, int y) {
        return grille.recup(x, y).recupContenuCase();
    }

    /**
     * Méthode afficherGrille qui affiche la grille
     */
    public void afficherGrille() {
        System.out.println(grille);
    }

    /**
     * Méthode recupLargeur qui renvoie la largeur de la grille
     *
     * @return la largeur de la grille
     */
    public int recupLargeur() {
        return grilleSolution.recupLargeur();
    }

    /**
     * Méthode qui renvoie la hauteur de la grille
     *
     * @return la hauteur de la grille
     */
    public int getHauteur() {
        return grilleSolution.recupHauteur();
    }

    /**
     * Méthode qui renvoie l'état de la partie
     *
     * @return l'état de la partie sous forme d'entier
     */
    public GridPane getGridPane() {
        return this.gridPane;
    }

    /**
     * Méthode qui renvoie la grille graphique (grille contenant la GridPane du jeu)
     * @return l'état de la partie sous forme d'entier
     */
    public Grille<CaseGraphique> getGrilleGraphique() {
        return grilleGraphique;
    }

    public void undo() {
        coup(pileUndo, pileRedo, 2);
    }

    public void redo() {
        coup(pileRedo, pileUndo, 1);
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
        if (coupPris.recupX() != -1) {
            for (int i = 0; i < nbClics; i++)
                grilleGraphique.recup(coupPris.recupX(), coupPris.recupY()).actionClic();
        }
        aPush.empiler(coupPris);
    }

    public boolean recupEtatPartie() {
        return etatPartie;
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
        for (int x = 0; x < grille.recupLargeur(); x++) {
            for (int y = 0; y < grille.recupHauteur(); y++) {
                String contenuGrille = grille.recup(x, y).recupContenuCase();
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
     * méthode servant à réinitialiser le niveau courant
     * on réinitialise le chronomètre en faisant appel à sa méthode reset_all (on fait de même pour le score)
     * on supprime la sauvegarde du niveau actuel si elle existait
     */
    public void reset() {
        chrono.reset_all();
        score.reset_all();
        try {
            //Voir #charger_niveau
            File sauvegarde = new File(Niveau.pathSauvegarde + cheminNiveau.substring(27) + modeJeu);
            if (sauvegarde.exists()) {
                if (!sauvegarde.delete()) throw new IOException("Unable to delete " + sauvegarde);
            }

            initialiser();
            sauvegarderNiveau();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}