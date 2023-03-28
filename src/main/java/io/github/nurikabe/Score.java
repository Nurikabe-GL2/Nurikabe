package io.github.nurikabe;

import java.io.Serializable;



public class Score implements Serializable{

    private int score=1500;

    public Score(int score){
      this.score=score;
    }

    public void reset_all(){
      score=1500;
    }

    public void retirerScore(int nb){
      score-=nb;
    }

    public int getScore(){
      return score;
    }

    public void setScore(int s){
      score=s;
    }
    
}