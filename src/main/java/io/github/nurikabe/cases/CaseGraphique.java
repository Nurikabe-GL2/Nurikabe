package io.github.nurikabe.cases;

import io.github.nurikabe.Coup;
import io.github.nurikabe.cases.Case.Type;
import io.github.nurikabe.niveaux.Niveau;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Classe représentant une case de la grille, sur l'interface.
 */
public class CaseGraphique extends StackPane implements ObservateurCase {
    private final Niveau grille;
    private final Case aCase;
    private Type type;

    public CaseGraphique(int x, int y, Niveau grille) {
        this.aCase = grille.recupCase(x, y);
        this.type = aCase.getType();
        this.grille = grille;

        aCase.ajouterObservateur(this);

        setPrefSize(30, 30);

        if (this.type != Type.NOMBRE) {
            setOnMouseClicked(e -> {
                // modifie le contenu graphque de la case, on sauvegarde le niveau, on lance la méthode
                // victoire pour voir si on a gagné la partie
                actionClic();
                grille.recupUndo().empiler(new Coup(x, y));
                grille.recupRedo().vider();
                grille.notifierChangement();
                grille.sauvegarderNiveau();

                //Verification victoire après l'insertion d'une case noire
                if (type == Type.NOIR)
                    grille.victoire();
            });
        }

        mettreAJour();
    }

    public void mettreAJour() {
        switch (type) {
            case BLANC -> {
                getChildren().clear();
                setClassesCss("caseblanche");
            }
            case NOIR -> setClassesCss(aCase.estAffecteParHypothese() ? "casenoireBleue" : "casenoire");
            case POINT -> {
                if (aCase.getIndice() > 0) {
                    Text indiceChemin = new Text(Integer.toString(aCase.getIndice()));
                    indiceChemin.getStyleClass().add("chiffreChemin");
                    setClassesCss("caseblanche");
                    setNodes(indiceChemin);
                } else {
                    mettreCercle();
                }
            }
            case NOMBRE -> {
                Text nb = new Text(aCase.getContenuCase());
                setNodes(nb);
                setClassesCss("caseblanche");
            }
        }
    }

    /**
     * Méthode actionClic gérant la réaction de la case au clic, elle s'occupe de changer l'état de la case de façon cyclique et vérifie si la grille est terminée
     */
    public void actionClic() {
        if (grille.estEnModeHypothese()) grille.actionHypothese();
        aCase.setAffecteParHypothese(grille.estEnModeHypothese());

        this.type = switch (type) {
            case POINT -> Type.BLANC;
            case NOIR -> Type.POINT;
            case BLANC -> Type.NOIR;
            default -> throw new IllegalArgumentException("Type non cliquable: " + type);
        };
        aCase.setType(type);

        //Suppression des aides si la case est remplie avec le bon type
        if (type == Type.POINT) getStyleClass().removeIf(s -> s.equals("cible-point"));
        if (type == Type.NOIR) getStyleClass().removeIf(s -> s.equals("cible-noir"));

        grille.calculerIndices();

        mettreAJour();
    }

    /**
     * Méthode privée pour mettre un cercle dans la case quand la case se trouve dans l'état point
     */
    private void mettreCercle() {
        Circle cercle = new Circle(10, 10, 7);
        cercle.setFill(grille.estEnModeHypothese() ? Color.BLUE : Color.BLACK);
        setNodes(cercle);
        setClassesCss("caseblanche");
    }

    /**
     * Met la case en surbrillance lors du positionnement des techniques sur la grille
     *
     * @param type Le type de la case qui devrait être insérée
     */
    public void surbrillance(Type type) {
        if (this.type == Type.NOMBRE)
            throw new IllegalStateException("Ne peut pas mettre une case nombre en surbrillance.");

        switch (type) {
            case POINT -> getStyleClass().add("cible-point");
            case NOIR -> getStyleClass().add("cible-noir");
            default -> throw new IllegalArgumentException("Type de case en surbrillance invalide: " + type);
        }
    }

    private void setClassesCss(String... classes) {
        getStyleClass().setAll(classes);
    }

    private void setNodes(Node... nodes) {
        getChildren().setAll(nodes);
    }

    @Override
    public void onChangement() {
        mettreAJour();
    }
}