package io.github.nurikabe;

/**
 * Classe Niveau pour représenter un niveau
 */
public class Hypothese {
    private final Niveau niv;

    /**
     * Nombre d'undo à faire si hypothèse annulée
     */
    private int nombreUndoToDo = 0;

    public Hypothese(Niveau niv) {
        this.niv = niv;
    }

    /**
     * Confirme l'hypothèse et met à jour la grille.
     */
    public void confirmer() {
        razActions();
        niv.desactiverModeHypothese();
        niv.majGrilles();
    }

    /**
     * Augmente le nombre d'undo à effectuer en cas d'annulation.
     */
    public void incrementerActions() {
        nombreUndoToDo++;
    }

    /**
     * Remet à zero le nombre d'undo à effectuer.
     */
    public void razActions() {
        nombreUndoToDo = 0;
    }

    /**
     * Annule l'hypothèse en cours en effectuant le nombre d'undo enregistré.
     */
    public void annuler() {
        System.out.println("nombre undo à faire : " + nombreUndoToDo);
        niv.desactiverModeHypothese();
        for (int i = 0; i < nombreUndoToDo; i++) {
            niv.undo();
        }
        razActions();
        niv.majGrilles();
    }
}