package io.github.nurikabe.niveaux;

public interface ObservateurNiveau {
    void onInitialiser();

    void onChangement();

    void onVictoire();
}
