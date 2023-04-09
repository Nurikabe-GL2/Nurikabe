package io.github.nurikabe.cases;

public class CaseSolution {
    private final String contenu;
    private final Case.Type type;

    public CaseSolution(String contenu) {
        this.contenu = contenu;
        this.type = Case.Type.depuisTexte(contenu);
    }

    public Case.Type getType() {
        return type;
    }

    public String getContenuCase() {
        return contenu;
    }

    public int getNombre() {
        if (type != Case.Type.NOMBRE)
            throw new IllegalStateException("Cette case n'est pas un nombre");
        return Integer.parseInt(contenu);
    }
}
