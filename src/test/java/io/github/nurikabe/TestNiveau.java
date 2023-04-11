package io.github.nurikabe;

import io.github.nurikabe.cases.Case;
import io.github.nurikabe.niveaux.FichierSolution;
import io.github.nurikabe.niveaux.MetadonneesSauvegarde;
import io.github.nurikabe.niveaux.Niveau;
import io.github.nurikabe.niveaux.Niveaux;
import io.github.nurikabe.techniques.Cible;
import io.github.nurikabe.techniques.PositionTechniques;
import io.github.nurikabe.techniques.Techniques;
import io.github.nurikabe.techniques.demarrage.IleDeUn;
import io.github.nurikabe.techniques.demarrage.IndiceAdjacentsEnDiagonale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestNiveau {
    private FichierSolution solution;
    private MetadonneesSauvegarde metadonneesSauvegarde;
    private Niveau niveau;

    /**
     * Charge le niveau à chaque fois qu'un test est lancé
     */
    @BeforeEach
    public void chargerMetadonneesSauvegarde() throws Exception {
        solution = Niveaux.getNiveaux(Difficulte.FACILE).get(0);
        metadonneesSauvegarde = solution.getMetadonneesSauvegarde(ModeDeJeu.CLASSIQUE);
        metadonneesSauvegarde.supprimerSauvegarde();

        niveau = new Niveau(metadonneesSauvegarde);
        niveau.initialiser();
    }

    /**
     * Teste si la classe {@link MetadonneesSauvegarde} detecte que la sauvegarde n'existe pas
     */
    @Test
    public void sauvegardeSupprimee() {
        assertFalse(metadonneesSauvegarde.contiensSauvegarde());
        assertFalse(metadonneesSauvegarde.estComplete());
        assertFalse(metadonneesSauvegarde.contiensSauvegarde());
    }

    /**
     * Teste la lecture des niveaux (largeur, hauteur, mode hypothèse désactivé, undo/redo vide...)
     * et si toutes les cases sont blanches ou des nombres
     */
    @Test
    public void chargerNiveau() {
        assertEquals(solution.getGrille().getLargeur(), niveau.getLargeur());
        assertEquals(solution.getGrille().getHauteur(), niveau.getHauteur());
        assertFalse(niveau.estEnModeHypothese());
        assertTrue(niveau.recupUndo().estVide());
        assertTrue(niveau.recupRedo().estVide());

        //Vérification que toutes les cases sont des nombres ou des
        for (int x = 0; x < niveau.getLargeur(); x++) {
            for (int y = 0; y < niveau.getHauteur(); y++) {
                final Case.Type type = niveau.recupCase(x, y).getType();
                if (type != Case.Type.BLANC && type != Case.Type.NOMBRE) {
                    fail("La grille initiale devrait contenir que des cases blanches et des nombres");
                }
            }
        }
    }

    /**
     * Teste les piles undo / redo
     */
    @Test
    public void undoRedo() {
        niveau.recupCase(0, 0).onClic();
        niveau.recupCase(0, 0).onClic();

        assertEquals(2, niveau.recupUndo().taille());
        assertEquals(0, niveau.recupRedo().taille());

        niveau.undo();
        assertEquals(Case.Type.NOIR, niveau.recupCase(0, 0).getType());

        niveau.undo();
        assertEquals(Case.Type.BLANC, niveau.recupCase(0, 0).getType());

        assertEquals(0, niveau.recupUndo().taille());
        assertEquals(2, niveau.recupRedo().taille());
    }

    /**
     * Teste les coordonnées en dehors de la grille, celles-ci doivent renvoyer des exceptions
     */
    @Test
    public void coordonneesGrille() {
        assertThrows(IllegalArgumentException.class, () -> niveau.recupCase(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> niveau.recupCase(0, -1));
        assertThrows(IllegalArgumentException.class, () -> niveau.recupCase(niveau.getLargeur(), 0));
        assertThrows(IllegalArgumentException.class, () -> niveau.recupCase(0, niveau.getHauteur()));

        //Clic case nombre
        assertThrows(IllegalStateException.class, () -> niveau.recupCase(2, 1).onClic());
    }

    /**
     * Teste si les cases sont transformées avec le bon type à chaque clic
     * <p>
     * blanc -> noir -> point
     * blanc <- noir <- point
     */
    @Test
    public void cycleCase() {
        final Case aCase = niveau.recupCase(0, 0);

        aCase.etatSuivant();
        assertEquals(Case.Type.NOIR, aCase.getType());

        aCase.etatSuivant();
        assertEquals(Case.Type.POINT, aCase.getType());

        aCase.etatSuivant();
        assertEquals(Case.Type.BLANC, aCase.getType());

        aCase.etatPrecedent();
        assertEquals(Case.Type.POINT, aCase.getType());

        aCase.etatPrecedent();
        assertEquals(Case.Type.NOIR, aCase.getType());

        aCase.etatPrecedent();
        assertEquals(Case.Type.BLANC, aCase.getType());
    }

    /**
     * Teste le mode hypothèse, vérifie que les cases sont marquées comme étant affectées,
     * que les piles undo/redo sont remises en l'état,
     * et que les changements sont annulés dans le cas de l'annulation de l'hypothèse
     */
    @Test
    public void hypothese() {
        final Case aCase = niveau.recupCase(0, 0);

        //Annulation hypothèse
        niveau.activerModeHypothese();

        aCase.onClic();
        assertTrue(aCase.estAffecteParHypothese());
        assertEquals(1, niveau.recupUndo().taille());

        niveau.annulerHypothese();
        assertFalse(aCase.estAffecteParHypothese());
        assertEquals(0, niveau.recupUndo().taille());

        assertEquals(Case.Type.BLANC, aCase.getType());

        //Confirmation hypothèse
        niveau.activerModeHypothese();

        aCase.onClic();
        assertTrue(aCase.estAffecteParHypothese());
        assertEquals(1, niveau.recupUndo().taille());

        niveau.confirmerHypothese();
        assertFalse(aCase.estAffecteParHypothese());
        assertEquals(1, niveau.recupUndo().taille());

        assertEquals(Case.Type.NOIR, aCase.getType());
    }

    /**
     * Teste la sauvegarde des niveaux, si le mode hypothèse est bien sauvegardé
     * et que les cases ont le bon type
     */
    @Test
    public void sauvegarde() throws Exception {
        niveau.recupCase(0, 0).onClic();
        //Vérification sauvegarde automatique
        assertTrue(metadonneesSauvegarde.contiensSauvegarde());

        niveau.activerModeHypothese();
        niveau.recupCase(1, 0).onClic();

        // Rechargement du niveau
        niveau = new Niveau(metadonneesSauvegarde);
        niveau.initialiser();

        assertFalse(niveau.recupCase(0, 0).estAffecteParHypothese());
        assertTrue(niveau.recupCase(1, 0).estAffecteParHypothese());

        //Vérification de la sauvegarde de l'annulation
        niveau.annulerHypothese();
        niveau = new Niveau(metadonneesSauvegarde);
        niveau.initialiser();
        assertEquals(Case.Type.BLANC, niveau.recupCase(1, 0).getType());
    }

    /**
     * Teste la vérification de la grille en insérant une erreur
     */
    @Test
    public void verification() {
        final int verifVide = niveau.verifier();
        niveau.recupCase(0, 0).onClic(); //Ajout case incorrecte
        assertEquals(verifVide + 1, niveau.verifier());
    }

    /**
     * Test des techniques, pour le premier niveau cela couvrira les 2 îles de 1, et les cases adjacentes en diagonale
     */
    @Test
    public void techniques() {
        PositionTechniques positionTechniques = Techniques.trouverTechnique(niveau);
        assertNotNull(positionTechniques);
        assertInstanceOf(IleDeUn.class, positionTechniques.getTechnique());

        for (Cible cible : positionTechniques.getCibles()) {
            niveau.recupCase(cible.x(), cible.y()).onClic();
        }

        positionTechniques = Techniques.trouverTechnique(niveau);
        assertNotNull(positionTechniques);
        assertInstanceOf(IleDeUn.class, positionTechniques.getTechnique());

        for (Cible cible : positionTechniques.getCibles()) {
            niveau.recupCase(cible.x(), cible.y()).onClic();
        }

        positionTechniques = Techniques.trouverTechnique(niveau);
        assertNotNull(positionTechniques);
        assertInstanceOf(IndiceAdjacentsEnDiagonale.class, positionTechniques.getTechnique());

        for (Cible cible : positionTechniques.getCibles()) {
            niveau.recupCase(cible.x(), cible.y()).onClic();
        }
    }
}
