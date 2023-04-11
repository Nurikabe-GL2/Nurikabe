package io.github.nurikabe.cases;

import io.github.nurikabe.Parametres;
import io.github.nurikabe.niveaux.Niveau;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class IndiceCases {
    private final Niveau grille;
    private List<Case> anciennesCases = new ArrayList<>();

    public IndiceCases(Niveau grille) {
        this.grille = grille;
    }

    public void calculerIndices() {
        if (Parametres.getParametres().doitNumeroterChemin()) {
            // Application d'une recherche en *largeur*, en prenant chaque case nombre comme point de départ
            final List<Case> cases = new ArrayList<>();
            final Deque<Case> aVisiter = new LinkedList<>();
            for (int y = 0; y < grille.getGrille().getHauteur(); y++) {
                for (int x = 0; x < grille.getGrille().getLargeur(); x++) {
                    if (estCoordonneeValide(x, y) && grille.recupCase(x, y) instanceof CaseNombre caseNombre) {
                        final Consumer<Case> consumerNumeroInitial = getAjouterCaseConsumer(cases, aVisiter, Integer.parseInt(caseNombre.getContenuCase()));

                        siCasePoint(x + 1, y, consumerNumeroInitial);
                        siCasePoint(x - 1, y, consumerNumeroInitial);
                        siCasePoint(x, y + 1, consumerNumeroInitial);
                        siCasePoint(x, y - 1, consumerNumeroInitial);
                        while (!aVisiter.isEmpty()) {
                            final Case aCase = aVisiter.removeFirst();
                            final Consumer<Case> consumerCasePoint = getAjouterCaseConsumer(cases, aVisiter, aCase.getIndice());

                            siCasePoint(aCase.getX() + 1, aCase.getY(), consumerCasePoint);
                            siCasePoint(aCase.getX() - 1, aCase.getY(), consumerCasePoint);
                            siCasePoint(aCase.getX(), aCase.getY() + 1, consumerCasePoint);
                            siCasePoint(aCase.getX(), aCase.getY() - 1, consumerCasePoint);
                        }
                    }
                }
            }

            for (Case aCase : cases) {
                aCase.notifierObservateurs();
            }

            anciennesCases.removeAll(cases); //Ne pas réinitialiser les cases ayant été recalculées
            for (Case ancienneCase : anciennesCases) {
                ancienneCase.setIndice(-1);
                ancienneCase.notifierObservateurs();
            }

            anciennesCases = cases;
        }
    }

    private Consumer<Case> getAjouterCaseConsumer(List<Case> cases, Deque<Case> aVisiter, int indiceActuel) {
        return e -> {
            if (!cases.contains(e)) {
                aVisiter.add(e);
                cases.add(e);
                e.setIndice(indiceActuel - 1);
            }
        };
    }

    private boolean estCoordonneeValide(int x, int y) {
        return x >= 0 && y >= 0 && y < grille.getHauteur() && x < grille.getLargeur();
    }

    /**
     * Exécute le consumer si la case est dans la grille et est un point
     */
    private void siCasePoint(int x, int y, Consumer<Case> consumer) {
        if (!estCoordonneeValide(x, y))
            return;
        final Case aCase = grille.recupCase(x, y);
        if (aCase.getType() == Case.Type.POINT) {
            consumer.accept(aCase);
        }
    }
}
