package io.github.nurikabe.techniques.donnees;

public class DonneesTechnique {
    private final String categorie, nom, description, condition, cheminImage;

    DonneesTechnique(String categorie, String nom, String description, String condition, String cheminImage) {
        this.categorie = categorie;
        this.nom = nom;
        this.description = description;
        this.condition = condition;
        this.cheminImage = cheminImage;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getCondition() {
        return condition;
    }

    public String getCheminImage() {
        return cheminImage;
    }

    public String getCategorie() {
        return categorie;
    }

}
