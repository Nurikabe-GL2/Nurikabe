package io.github.nurikabe.niveaux;

import io.github.nurikabe.*;
import io.github.nurikabe.cases.Case;
import io.github.nurikabe.cases.CaseNombre;
import io.github.nurikabe.cases.CaseNormale;
import io.github.nurikabe.cases.CaseSolution;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Technique;
import io.github.nurikabe.techniques.Techniques;
import io.github.nurikabe.techniques.demarrage.IleDeUn;
import io.github.nurikabe.techniques.demarrage.IndiceAdjacentsEnDiagonale;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Classe Niveau pour représenter un niveau
 */
public class Niveau {
    private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor(r -> {
        final Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    private final Parametres parametres = Parametres.getParametres();
    private final MetadonneesSauvegarde metadonneesSauvegarde;
    private final ScheduledFuture<?> saveFuture;

    /**
     * Variable d'instance grille qui représente le contenu de la grille sous forme d'une ArrayList
     */
    private Grille<Case> grille;

    /**
     * Variable d'instance grilleSolution représentant la solution de la grille
     */
    private final Grille<CaseSolution> grilleSolution;

    /**
     * Variable d'instance pileUndo représentant la pile servant au bouton Undo
     */
    private Pile pileUndo;

    /**
     * Variable d'instance pileRedo représentant la pile servant au bouton Redo
     */
    private Pile pileRedo;

    private Hypothese hypo;

    private Chronometre chrono;
    private Score score;

    private final List<ObservateurNiveau> observateursNiveau = new ArrayList<>();

    /**
     * Constructeur de la classe Niveau
     */
    public Niveau(MetadonneesSauvegarde metadonneesSauvegarde) throws Exception {
        this.metadonneesSauvegarde = metadonneesSauvegarde;
        this.grilleSolution = metadonneesSauvegarde.getSolution().getGrille();

        //Sauvegarder le temps toutes les secondes seulement en mode contre-la-montre
        if (metadonneesSauvegarde.getModeDeJeu() == ModeDeJeu.CONTRE_LA_MONTRE)
            saveFuture = EXECUTOR.scheduleWithFixedDelay(this::sauvegarderNiveau, 1, 1, TimeUnit.SECONDS);
        else saveFuture = null;
    }

    public void ajouterObservateur(ObservateurNiveau observateurNiveau) {
        observateursNiveau.add(observateurNiveau);
    }

    public void quitter() {
        if (saveFuture != null)
            saveFuture.cancel(false);
    }

    /*
     * Méthode servant à initialiser la partie.
     * C'est ici qu'on crée les piles pour les boutons undo/redo, qu'on charge le niveau
     * et sa sauvegarde s'il en existe une (la grille du niveau existante, les piles undo/redo etc...)
     * on charge le chronomètre et le score (qui seront affichés si nous sommes en mode ContreLaMontre)
     */
    public void initialiser() throws Exception {
        pileUndo = new Pile();
        pileRedo = new Pile();
        score = new Score(1500);
        chrono = new Chronometre();
        hypo = new Hypothese();
        chargerGrille();

        for (ObservateurNiveau observateurNiveau : observateursNiveau) {
            observateurNiveau.onInitialiser();
        }
    }

    /*
     * Récuperer la grille contenant la solution du niveau
     */
    public Grille<CaseSolution> getGrilleSolution() {
        return grilleSolution;
    }

    /**
     * Méthode chargerGrille qui s'occupe de charger la grille.
     * On commence par charger la solution du niveau, puis on tente de charger sa sauvegarde
     * s'il n'en existe pas on le charge directement en mettant les cases à 0 (vides)
     */
    private void chargerGrille() throws Exception {
        final boolean aChargeSauvegarde = chargerSauvegarde();
        if (!aChargeSauvegarde) {
            //Pas de sauvegarde, création de la grille
            grille = new Grille<>(grilleSolution.getLargeur(), grilleSolution.getHauteur());

            final Case.Type typeCaseDepart = parametres.doitRemplirCasesNoires()
                    ? Case.Type.NOIR
                    : Case.Type.BLANC;
            for (int y = 0; y < grille.getHauteur(); y++) {
                for (int x = 0; x < grille.getLargeur(); x++) {
                    final CaseSolution caseSolution = grilleSolution.recup(x, y);
                    final Case uneCase = switch (caseSolution.getType()) {
                        case BLANC, NOIR -> new CaseNormale(x, y, typeCaseDepart);
                        case NOMBRE -> new CaseNombre(x, y, caseSolution.getNombre());
                        default ->
                                throw new IllegalArgumentException("Type de case solution inattendu: " + caseSolution.getType());
                    };
                    grille.mettre(x, y, uneCase);
                }
            }
        }

        for (int y = 0; y < grille.getHauteur(); y++) {
            for (int x = 0; x < grille.getLargeur(); x++) {
                grille.recup(x, y).setNiveau(this);
            }
        }

        //Utilisation des techniques basiques dans le cas d'une nouvelle partie
        if (!aChargeSauvegarde) {
            if (parametres.doitCompleterIleDeUn()) {
                //Utilisation de la technique "ile de 1" jusqu'à ce qu'il n'y en ait plus
                utiliserTechnique(IleDeUn.class);
            }

            if (parametres.doitCompleterCasesAdjacentes()) {
                //Utilisation de la technique "Indice adjacents en diagonale" jusqu'à ce qu'il n'y en ait plus
                utiliserTechnique(IndiceAdjacentsEnDiagonale.class);
            }
        }
    }

    private <T extends Technique> void utiliserTechnique(Class<T> typeTechnique) {
        PositionTechniques positionTechniques;
        while ((positionTechniques = Techniques.trouverTechnique(this)) != null && typeTechnique.isInstance(positionTechniques.getTechnique())) {
            for (Cible cible : positionTechniques.getCibles()) {
                recupCase(cible.x(), cible.y()).etatSuivant();
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
     * Méthode appelée lors de l'utilisation du bouton aide
     * on sauvegarde le niveau et on retire 100 points au score (pénalité pour l'utilisation de l'aide)
     */
    public void utilisationAide() {
        if (score.getScore() > 0) score.retirerScore(100);
        notifierChangement();
        sauvegarderNiveau();
    }

    public void notifierChangement() {
        for (ObservateurNiveau observateurNiveau : observateursNiveau) {
            observateurNiveau.onChangement();
        }
    }

    private void notifierVictoire() {
        for (ObservateurNiveau observateurNiveau : observateursNiveau) {
            observateurNiveau.onVictoire();
        }
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
        hypo = sauvegarde.getHypothese();

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
            saveFuture.cancel(false);
            metadonneesSauvegarde.marquerComplet();

            notifierVictoire();
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

    public Grille<Case> getGrille() {
        return grille;
    }

    public void undo() {
        if (pileUndo.estVide()) return;

        final Coup coup = pileUndo.depiler();
        if (coup.etaitPrecedent()) recupCase(coup.x(), coup.y()).etatSuivant();
        else recupCase(coup.x(), coup.y()).etatPrecedent();
        pileRedo.empiler(coup);

        notifierChangement();
    }

    public void redo() {
        if (pileRedo.estVide()) return;

        final Coup coup = pileRedo.depiler();
        if (coup.etaitPrecedent()) recupCase(coup.x(), coup.y()).etatPrecedent();
        else recupCase(coup.x(), coup.y()).etatSuivant();
        pileUndo.empiler(coup);

        notifierChangement();
    }

    private void onFinModeHypothese() {
        for (int y = 0; y < getHauteur(); y++) {
            for (int x = 0; x < getLargeur(); x++) {
                grille.recup(x, y).setAffecteParHypothese(false);
            }
        }
        sauvegarderNiveau();
    }

    public boolean estEnModeHypothese(){
        return hypo.estActif();
    }

    public void activerModeHypothese() {
        hypo.nouvelleHypothese();
        notifierChangement();
    }

    public void actionHypothese() {
        hypo.incrementerActions();
    }

    public void confirmerHypothese() {
        hypo.confirmer();
        notifierChangement();
        onFinModeHypothese();
    }

    public void annulerHypothese() {
        hypo.annuler(this);
        notifierChangement();
        onFinModeHypothese();
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
                var contenuGrille = grille.recup(x, y).getType();
                if (contenuGrille == Case.Type.POINT) contenuGrille = Case.Type.BLANC;

                final var contenuSolution = grilleSolution.recup(x, y).getType();
                if (contenuGrille != contenuSolution) {
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

    public Hypothese getHypothese() {
        return hypo;
    }
}