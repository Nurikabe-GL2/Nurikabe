package io.github.nurikabe.cases;

import io.github.nurikabe.Coup;

/**
 * La classe CaseNormale hérite de la classe abstraite Case et représente les cases qui sont noires ou blanches
 */
public class CaseNormale extends Case {
    /**
     * Constructeur de la classe CaseNormale
     *
     * @param x    la coordonnée x de la case
     * @param y    la coordonnée y de la case
     * @param type Le type de la case, blanc ou noir
     */
    public CaseNormale(int x, int y, Type type) {
        super(x, y, type);
    }

    /**
     * Méthode redéfinie recupContenuCase renvoyant le contenu de la case sous forme de chaîne de caractères
     *
     * @return le contenu de la case
     */
    @Override
    public String getContenuCase() {
        return switch (type) {
            case BLANC -> "b";
            case NOIR -> "n";
            case POINT -> ".";
            default -> throw new IllegalArgumentException("Type invalide: " + type);
        };
    }

    @Override
    public void etatSuivant() {
        this.type = switch (type) {
            case POINT -> Type.BLANC;
            case NOIR -> Type.POINT;
            case BLANC -> Type.NOIR;
            default -> throw new IllegalArgumentException("Type non cliquable: " + type);
        };

        notifierObservateurs();
        niveau.sauvegarderNiveau();
    }

    @Override
    public void etatPrecedent() {
        this.type = switch (type) {
            case POINT -> Type.NOIR;
            case NOIR -> Type.BLANC;
            case BLANC -> Type.POINT;
            default -> throw new IllegalArgumentException("Type non cliquable: " + type);
        };

        notifierObservateurs();
        niveau.sauvegarderNiveau();
    }

    @Override
    public void onClic() {
        onClic(false);
    }

    @Override
    public void onClicPrecedent() {
        onClic(true);
    }

    private void onClic(boolean precedent) {
        if (niveau.estEnModeHypothese()) niveau.actionHypothese();
        setAffecteParHypothese(niveau.estEnModeHypothese());

        if (precedent) etatPrecedent();
        else etatSuivant();

        niveau.recupUndo().empiler(new Coup(x, y));
        niveau.recupRedo().vider();
        niveau.notifierChangement();
        niveau.sauvegarderNiveau();

        //Verification victoire après l'insertion d'une case noire
        if (type == Type.NOIR)
            niveau.victoire();
    }
}
