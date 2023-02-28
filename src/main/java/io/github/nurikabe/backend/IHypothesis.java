package io.github.nurikabe.backend;

import io.github.nurikabe.backend.tile.MutableTiles;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;

public interface IHypothesis {
    @NotNull
    MutableTiles getTiles();

    @NotNull
    Stack<Move> getUndoStack();

    @NotNull
    Stack<Move> getRedoStack();

    void touch(int col, int row);

    void undo();

    void redo();
}
