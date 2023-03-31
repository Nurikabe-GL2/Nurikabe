package io.github.nurikabe;

import java.io.*;
import java.nio.file.Path;

public class Parametres implements Serializable{
    private boolean remplirCases;
    private boolean numeroteChemin;
    private boolean afficheErreurs;
    private boolean completeTaille1;
    private boolean completeCaseAdj;
    private static Path cheminSauvegarde = Path.of("sauvegarde", "parametres", "parametres.ser");
    //Path.of("sauvegarde", modeDeJeu.recupNomMode(), IOUtils.replaceExtension(solution.getCheminNiveau(), "bin").getFileName().toString());

    public Parametres(){
        this.setDefaultParams();
    }
    
    public static Path getCheminSauvegarde(){
        return cheminSauvegarde;
    }

    public boolean getRemplirCases(){
        return this.remplirCases;
    }
    
    public boolean getNumeroteChemin(){
        return this.numeroteChemin;
    }
    
    public boolean getAfficheErreurs(){
        return this.afficheErreurs;
    }
    
    public boolean getCompleteTaille1(){
        return this.completeTaille1;
    }

    public boolean getCompleteCaseAdj(){
        return this.completeCaseAdj;
    }
    
    public void setRemplirCases(boolean remplirCases){
        this.remplirCases = remplirCases;
    }
    
    public void setNumeroteChemin(boolean numeroteChemin){
        this.numeroteChemin = numeroteChemin;
    }

    public void setAfficheErreurs(boolean afficheErreurs){
        this.afficheErreurs = afficheErreurs;
    }
    
    public void setCompleteTaille1(boolean completeTaille1){
        this.completeTaille1 = completeTaille1;
    }
    
    public void setCompleteCaseAdj(boolean completeCaseAdj){
        this.completeCaseAdj = completeCaseAdj;
    }
    
    public static Parametres getParams(){
        Parametres params = new Parametres();
        try(ObjectInputStream stream = new ObjectInputStream(IOUtils.newBufferedInputStream(cheminSauvegarde))) {
            params = (Parametres) stream.readObject();
            return params;
        } catch (IOException i) {
            i.printStackTrace();
            System.out.println("Pas de fichier sauvegardé, chargement des paramètres par défaut");
            params.setDefaultParams();
            return params;
        } catch (ClassNotFoundException c) {
            System.out.println("Pas de fichier sauvegardé, chargement des paramètres par défaut");
            c.printStackTrace();
            params.setDefaultParams();
            return params;
        } 
    }

    public void setDefaultParams(){
        setRemplirCases(false);
        setNumeroteChemin(true);
        setAfficheErreurs(true);
        setCompleteTaille1(false);
        setCompleteCaseAdj(false);
    }
    

    public String toString(){
        return "Objet parametres -> \n Remplissage: "+getRemplirCases()+
        "\n Numerotage des chemins: "+getNumeroteChemin()+
        "\n Affichage des erreurs: "+getAfficheErreurs()+
        "\n Completer les iles de taille 1: "+getCompleteTaille1()+
        "\n Completion cases adjacentes: "+getCompleteCaseAdj();
    }
}
