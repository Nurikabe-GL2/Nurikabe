package io.github.nurikabe;

import java.io.Serializable;


/**
 * CLasse Chronometre
 * classe contenant les données du temps d'une partie en cours (ne sera affiché que lors d'une partie en mode Course Contre la montre)
 */
public class Chronometre implements Serializable{

  /**
   * debut et fin : représentent le temps (long) avec la méthode currentTimeMillis() de la classe System
   * ensuite les autres variables servent à convertir le temps en seconde et minutes puis à l'afficher
   * avec des String 
   */
    private long debut;
    private long fin;
    private int minutes=0, secondes=0;
    private int temps_supp=0;
    private String minutes_s="00", secondes_s="00";

    /*
     * constructeur Chronometre, on appelle les méthodes de début et fin pour initialiser les variables fin et début
     */
    public Chronometre(){
      debut();
      fin();
    }

     /*
     * Méthode appelée pour relancer le chronomètre courant à 0
     */
    public void reset(){
      debut();
      fin();
    }

     /*
      * méthode appelée lors de la réinitialisation d'un niveau
     */
    public void reset_all(){
      debut();
      fin();
      temps_supp=0;
    }
    /*
      * méthode appelée pour sauvegarder un niveau (on garde le temps écoulé dans une variable)
     */
    public void sauvegarder(){
      temps_supp=getTempsEcoule();
      reset();
    }

    /*
     * méthode pour actualiser la variable début
     */
    public void debut() {
        debut = System.currentTimeMillis();
    }

    /*
     * méthode pour actualiser la variable fin
     */
    public void fin() {
        fin = System.currentTimeMillis();
    }

    /*
     * méthode pour récupérer le temps écoulé (debut-fin) + le temps écoulé sauvegardé
     */
    private int getTempsEcoule() {
        fin();
       // System.out.println(fin-debut);
        return (int)(long)(fin - debut)+temps_supp;
    }

    /*
     * méthode pour convertir le temps écoulé en String
     * on commence par calculer les secondes puis ensuite les minutes (le calcul est fait à parti de milisecondes)
     * La méthode currentTimeMillis renvoie le temps courant en milisecondes
     */
    private void convertir_temps_ecoule(){
        int temps_ecoule=getTempsEcoule();
        if(temps_ecoule<60000){
          secondes=temps_ecoule/1000;
        }
        else{

          minutes=temps_ecoule/60000;
          temps_ecoule-=minutes*60000;
          secondes=temps_ecoule/1000;

        }

        if(secondes<10)secondes_s="0"+secondes;
        else secondes_s=secondes+"";

        if(minutes<10)minutes_s="0"+minutes;
        else minutes_s=minutes+"";
      
    }

    /**
     * Redéfinition de la méthode toString pour afficher le chronomètre
     * Cette méthode fait appel à la méthode convertir_temps_écouler
     * comme ça lors de l'affichage du chronomètre, ce dernier s'actualisera automatiquement
     */
    @Override
    public String toString(){

      convertir_temps_ecoule();
      return minutes_s+":"+secondes_s;
      
    }
}