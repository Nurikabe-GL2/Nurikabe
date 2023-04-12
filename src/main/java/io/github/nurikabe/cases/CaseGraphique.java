package io.github.nurikabe.cases;

import io.github.nurikabe.Parametres;
import io.github.nurikabe.cases.Case.Type;
import io.github.nurikabe.controller.NiveauController;
import io.github.nurikabe.niveaux.Niveau;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
    private final NiveauController niveauController;

    public CaseGraphique(int x, int y, Niveau grille, NiveauController niveauController) {
        this.niveauController = niveauController;
        this.aCase = grille.recupCase(x, y);
        this.grille = grille;

        aCase.ajouterObservateur(this);

        setPrefSize(30, 30);

        if (getType() != Type.NOMBRE) {
            setOnMouseClicked(this::actionClic);
        }

        mettreAJour();
    }

    public Type getType() {
        return aCase.type;
    }

    public void mettreAJour() {
        getChildren().clear();
        getStyleClass().clear();

        switch (getType()) {
            case BLANC -> setClassesCss("caseblanche");
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

        if (Parametres.getParametres().doitAfficherErreurs()) {
            //Cases *noires* erronées
            if (getType() == Type.NOIR && getType() != grille.getGrilleSolution().recup(aCase.getX(), aCase.getY()).getType()) {
                final Label labelErronnee = new Label("!");
                labelErronnee.getStyleClass().add("labelCaseErronee");
                setNodes(labelErronnee);
            }
        }
    }

    /**
     * Méthode actionClic gérant la réaction de la case au clic,
     * elle s'occupe de changer l'état de la case de façon cyclique et de retirer l'aide s'il y en avait une
     */
    private void actionClic(MouseEvent e) {
        switch (e.getButton()) {
            case PRIMARY -> aCase.onClic();
            case SECONDARY -> aCase.onClicPrecedent();
            default -> {
                return;
            }
        }

        //Suppression des aides si la case est remplie avec le bon type
        if (getType() == Type.POINT) getStyleClass().removeIf(s -> s.equals("cible-point"));
        if (getType() == Type.NOIR) getStyleClass().removeIf(s -> s.equals("cible-noir"));

        niveauController.calculerIndices();
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
        if (getType() == Type.NOMBRE)
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