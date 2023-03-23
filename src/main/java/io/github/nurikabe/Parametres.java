package io.github.nurikabe;

import java.io.*;

public class Parametres implements Serializable{
    private boolean remplirCases;
    private boolean numeroteChemin;
    private boolean afficheErreurs;
    private boolean completeTaille1;
    private boolean completeCaseAdj;
    private static String cheminSauvegarde = "./parametres.ser";

    public Parametres(){
        this.setDefaultParams();
    }
    
    public static String getCheminSauvegarde(){
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
    
    public Parametres getParams(){
        try {
            FileInputStream fileIn = new FileInputStream(cheminSauvegarde);
            ObjectInputStream in = new ObjectInputStream(fileIn);
             = (Parametres) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            System.out.println("Pas de fichier sauvegardé, chargement des paramètres par défaut");
            this.parametres.setDefaultParams();
            this.saveParams();
            this.chargerParams();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Pas de fichier sauvegardé, chargement des paramètres par défaut");
            c.printStackTrace();
            this.parametres.setDefaultParams();
            return;
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
