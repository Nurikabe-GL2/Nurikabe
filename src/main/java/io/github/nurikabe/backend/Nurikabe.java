package io.github.nurikabe.backend;

import io.github.nurikabe.backend.level.Level;
import io.github.nurikabe.backend.tile.MutableTiles;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class Nurikabe implements IHypothesis {
    /** Private hypotheses stack, always use the latest hypothesis provided by {@link #getHypothesis()} */
    private final Stack<Hypothesis> hypotheses = new Stack<>();
    private final List<NurikabeListener> listeners = new ArrayList<>();

    private final Level level;

    private Nurikabe(Level level) {
        this.level = level;

        listeners.add(new NurikabeListener() {
            @Override
            public void onTileChange(int col, int row) { save(); }

            @Override
            public void onHypothesisChange() { save(); }
        });
    }

    /**
     * @return The current hypothesis
     */
    public Hypothesis getHypothesis() {
        return hypotheses.lastElement();
    }

    // -------- These methods all delegate to the current hypothesis   --------
    // -------- i.e. All methods are relayed to the current hypothesis --------

    @NotNull
    @Override
    public MutableTiles getTiles() {
        return getHypothesis().getTiles();
    }

    @NotNull
    @Override
    public Stack<Move> getUndoStack() {
        return getHypothesis().getUndoStack();
    }

    @NotNull
    @Override
    public Stack<Move> getRedoStack() {
        return getHypothesis().getRedoStack();
    }

    @Override
    public void touch(int col, int row) {
        getHypothesis().touch(col, row);
    }

    @Override
    public void undo() {
        getHypothesis().undo();
    }

    @Override
    public void redo() {
        getHypothesis().redo();
    }

    // -------- --------

    public void fireEvents(Consumer<NurikabeListener> consumer) {
        for (NurikabeListener listener : listeners) {
            consumer.accept(listener);
        }
    }

    public void newHypothesis() {
        //TODO Créer une copy d'hypothèse, voir Hypothesis#copy
        // Envoyer un event comme quoi l'hypothèse à changé, avec Nurikabe#fireEvent
    }

    public void cancelHypothesis() {
        //TODO Retirer l'hypothèse courante de la pile
        // Envoyer un event comme quoi l'hypothèse à changé, avec Nurikabe#fireEvent
    }

    public void acceptHypothesis() {
        //TODO L'objectif est de remplacer l'hypothèse précédente par celle actuelle
        // Donc, retirer l'hypothèse courante de la pile et la garder de côté
        // Puis retirer une autre hypothèse
        // Puis rajouter l'hypothèse gardée de côté
        // Envoyer un event comme quoi l'hypothèse à changé, avec Nurikabe#fireEvent
    }

    public int check() {
        //TODO Renvoyer le nombre de cases différentes par rapport à la solution, voir Level#getSolution
        throw new UnsupportedOperationException();
    }

    private void save() {
        //TODO
    }

    public static Nurikabe fromNewLevel(Level level) {
        final Nurikabe nurikabe = new Nurikabe(level);
        nurikabe.hypotheses.push(new Hypothesis(nurikabe, level, Settings.getSettings().getStartColor()));

        //TODO appliquer les auto completions de base

        return nurikabe;
    }

    public static Nurikabe fromSavedLevel(Level level) {
        final Nurikabe nurikabe = new Nurikabe(level);
        //TODO chargement sauvegarde
//        nurikabe.hypotheses.push(...);

        return nurikabe;
    }
}
