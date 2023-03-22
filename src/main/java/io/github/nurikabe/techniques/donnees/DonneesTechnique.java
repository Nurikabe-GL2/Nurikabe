package io.github.nurikabe.techniques.donnees;

public class DonneesTechnique {
    private final String nom, description, condition, cheminImage;

    DonneesTechnique(String nom, String description, String condition, String cheminImage) {
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
}
