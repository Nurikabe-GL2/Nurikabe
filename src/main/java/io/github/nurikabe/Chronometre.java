package io.github.nurikabe;

import java.io.Serializable;


/**
 * faire appel à la classe
 *
 * Chronometre chrono = new Chronometre();
 * chrono.debut();
 * // lancer la partie
 * chrono.fin();
 * long tempsEcoule = chrono.getTempsEcoule();
 * System.out.println("Temps écoulé : " + tempsEcoule + " ms");
 */
public class Chronometre implements Serializable{

    private long debut;
    private long fin;
    private int minutes=0, secondes=0;
    private String minutes_s="00", secondes_s="00";

    public Chronometre(){
      debut();
      fin();
    }

    public void reset(){
      debut();
      fin();
    }

    public void debut() {
        debut = System.currentTimeMillis();
    }

    public void fin() {
        fin = System.currentTimeMillis();
    }

    private int getTempsEcoule() {
        fin();
       // System.out.println(fin-debut);
        return (int)(long)(fin - debut);
    }

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

    @Override
    public String toString(){

      convertir_temps_ecoule();
      return minutes_s+":"+secondes_s;
      
    }
/* 
    public void afficher_ecran(){

      if(label_chronometre!=null){
        label_chronometre.setText(toString());
      }
    } */
}