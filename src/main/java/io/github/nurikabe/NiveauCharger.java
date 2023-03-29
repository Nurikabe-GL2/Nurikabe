package io.github.nurikabe;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;


/**
 * Classe servant à charger les niveaux pour afficher les grilles miniatures des modes de jeu (classique et ContreLaMontre)
 */
public class NiveauCharger {
    /**
     * espace_boutons (pour les espaces qu'il faudra mettre entre les boutons des niveaux)
     * complete pour savoir si le niveau est complété ou non
     * sauv pour la grille sauvegardée
     * niveau pour la gridpane miniature (la grille qui sera affichée en petit)
     */
    private final boolean complete;
    private final String sauv;
    private final String cheminSolution;
    private final GridPane niveau;
    private int espaceBoutons;

    /**
     * Constructeur de la classe NiveauCharger
     *
     * @param cheminSolution le chemin pour récupérer le fichier à charger
     * @param modeDeJeu      le mode de jeu du niveau à charger
     */
    public NiveauCharger(String cheminSolution, ModeDeJeu modeDeJeu) {
        this.sauv = "sauvegarde/" + cheminSolution + modeDeJeu;
        this.cheminSolution = "src/main/resources/niveaux/" + cheminSolution;
        complete = niveauComplete(sauv);
        niveau = chargerNiveauGrilleMiniature();
    }

    /**
     * Méthode appelée pour charger la grille miniaturisée du niveau en question
     *
     * @return un objet de type GridPane qui sera affiché dans la sélection des niveaux
     */
    public static int fichierExiste(String nom) {
        File fichier = new File(nom);
        if (fichier.exists() && !fichier.isDirectory()) return 1;
        return 0;
    }

    private GridPane chargerNiveauGrilleMiniature() {
        try {
            GridPane gridpane = new GridPane();
            gridpane.getStylesheets().add("/css/Plateau.css");
            gridpane.setStyle("-fx-border-color: #51c264; -fx-border-width: 2.5; -fx-background-color: #FFFFFF;");
            Grille<String> grilleSolution = Niveau.chargerGrilleSolution(cheminSolution);
            espaceBoutons = grilleSolution.getLargeur() * 9;

            for (int y = 0; y < grilleSolution.getHauteur(); y++) {

                for (int x = 0; x < grilleSolution.getLargeur(); x++) {

                    StackPane p = new StackPane();
                    p.setPrefSize(20, 20);
                    if (!complete) {
                        if (grilleSolution.recup(x, y).equals("b") || grilleSolution.recup(x, y).equals("n"))
                            p.getStyleClass().add("caseblanche");
                    } else if (grilleSolution.recup(x, y).equals("n")) {
                        //p.getStyleClass().add("caseblanche");
                        p.getStyleClass().add("casenoire");
                    } else if (grilleSolution.recup(x, y).equals("b")) {
                        p.getStyleClass().add("caseblanche");
                    }

                    if (!grilleSolution.recup(x, y).equals("b") && !grilleSolution.recup(x, y).equals("n")) {
                        p.getStyleClass().add("caseblanche");
                        Text nb = new Text(grilleSolution.recup(x, y));
                        p.getChildren().add(nb);
                    }

                    GridPane.setRowIndex(p, y);
                    GridPane.setColumnIndex(p, x);
                    gridpane.getChildren().addAll(p);
                }
            }

            return gridpane;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Vérifie si un niveau est complété ou non
     *
     * @param nom nom du niveau à vérifier avec son chemin complet
     *
     * @return {@code true} si ce dernier est complété
     */
    public boolean niveauComplete(String nom) {
        try {
            if (NiveauCharger.fichierExiste(nom) == 1) {
                try (Scanner lire = new Scanner(new FileInputStream(nom))) {
                    String res = lire.nextLine();
                    if (res.equals("NIVEAU_COMPLETE")) {
                        System.out.println("niveau complete!");
                        return true;
                    }
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //TODO Très sus cette valeur
    public int getEspaceBoutons() {
        return espaceBoutons;
    }

    public GridPane getGridpane() {
        return niveau;
    }

    public boolean isComplete() {
        return complete;
    }
}