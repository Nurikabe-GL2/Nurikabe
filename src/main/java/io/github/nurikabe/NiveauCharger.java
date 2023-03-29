package io.github.nurikabe;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;


/**
 * Classe servant à charger charger les niveaux pour afficher les grilles miniatures des modes de jeu (classique et ContreLaMontre)
 */
public class NiveauCharger {
    /**
     * espace_boutons (pour les espaces qu'il faudra mettre entre les boutons des niveaux)
     * complete pour savoir si le niveau est complété ou non
     * sauv pour la grille sauvegardée
     * niveau pour la gridpane miniature (la grille qui sera affichée en petit)
     */
    private final int complete;
    private final String sauv;
    private final String cheminSolution;
    private final GridPane niveau;
    private int espaceBoutons;

    /**
     * constructeur de la classe NiveauCharger
     *
     * @param cheminSolution le chemin pour récuperer le fichier à charger
     * @param modeJeu        le mode de jeu du niveau à charger
     */
    public NiveauCharger(String cheminSolution, String modeJeu) {
        this.sauv = "sauvegarde/" + cheminSolution + modeJeu;
        this.cheminSolution = "src/main/resources/niveaux/" + cheminSolution;
        complete = niveauComplete(sauv);
        niveau = chargerNiveauGrilleMiniature();
    }

    /**
     * méthode appelée pour charger la grille miniaturisée du niveau en question
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
            //grille_solution=new String[largeur][hauteur];
            Grille<String> grilleSolution = Niveau.chargerGrilleSolution(cheminSolution);
            espaceBoutons = grilleSolution.recupLargeur() * 9;

            for (int y = 0; y < grilleSolution.recupHauteur(); y++) {

                for (int x = 0; x < grilleSolution.recupLargeur(); x++) {

                    StackPane p = new StackPane();
                    p.setPrefSize(20, 20);
                    if (complete == 0) {
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
     * savoir si un niveau est complété ou non
     *
     * @param nom nom du niveau à vérifier avec son chemin complet
     *
     * @return 1 si ce dernier est complété, 0 sinon
     */
    public int niveauComplete(String nom) {
        try {
            if (NiveauCharger.fichierExiste(nom) == 1) {
                try (Scanner lire = new Scanner(new FileInputStream(nom))) {
                    String res = lire.nextLine();
                    if (res.equals("NIVEAU_COMPLETE")) {
                        System.out.println("niveau complete!");
                        return 1;
                    }
                }
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    /**
     * getter pour l'espace des boutons
     *
     * @return un entier contenant l'espace à insérer entre les boutons
     */
    public int getEspaceBoutons() {
        return espaceBoutons;
    }

    /**
     * getter pour la gridpane miniature
     *
     * @return un objet de type GridPane
     */
    public GridPane getGridpane() {
        return niveau;
    }

    /**
     * savoir si le niveau est complété ou non en vérifiant que complete soit égal à 1
     *
     * @return Booleen
     */
    public boolean isComplete() {
        return (complete == 1);
    }
}