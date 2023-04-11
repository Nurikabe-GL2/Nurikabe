package io.github.nurikabe;

import io.github.nurikabe.cases.Case;
import io.github.nurikabe.cases.CaseSolution;
import io.github.nurikabe.niveaux.FichierSolution;
import io.github.nurikabe.niveaux.Niveaux;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSolutions {
    @Test
    public void chargementSolutions() {
        assertEquals(20, Niveaux.getNiveaux(Difficulte.FACILE).size());
        assertEquals(20, Niveaux.getNiveaux(Difficulte.MOYEN).size());
        assertEquals(20, Niveaux.getNiveaux(Difficulte.DIFFICILE).size());
    }

    @Test
    public void lectureSolution() {
        final FichierSolution solution = Niveaux.getNiveaux(Difficulte.FACILE).get(0);
        final Grille<CaseSolution> grille = solution.getGrille();

        assertEquals("Facile 1 (6x8)", solution.getNomNiveau());
        assertEquals(6, grille.getLargeur());
        assertEquals(8, grille.getHauteur());

        assertEquals(Case.Type.NOIR, grille.recup(0, 1).getType());
        assertEquals(Case.Type.NOIR, grille.recup(1, 1).getType());
        assertEquals(Case.Type.NOMBRE, grille.recup(2, 1).getType());
        assertEquals(Case.Type.NOIR, grille.recup(3, 1).getType());
        assertEquals(Case.Type.NOIR, grille.recup(4, 1).getType());
        assertEquals(Case.Type.BLANC, grille.recup(5, 1).getType());
    }
}
