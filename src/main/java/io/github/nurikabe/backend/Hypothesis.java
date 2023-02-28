package io.github.nurikabe.backend;

import io.github.nurikabe.backend.level.Level;
import io.github.nurikabe.backend.tile.*;
import org.jetbrains.annotations.NotNull;

import java.util.Stack;

public class Hypothesis implements IHypothesis {
    private final Nurikabe nurikabe;
    private final MutableTiles tiles;
    private final Stack<Move> undoStack;
    private final Stack<Move> redoStack;

    /** Copy constructor */
    private Hypothesis(Nurikabe nurikabe, MutableTiles tiles, Stack<Move> undoStack, Stack<Move> redoStack) {
        this.nurikabe = nurikabe;
        this.tiles = tiles;
        this.undoStack = undoStack;
        this.redoStack = redoStack;
    }

    public Hypothesis(Nurikabe nurikabe, MutableTiles tiles) {
        this.nurikabe = nurikabe;
        this.tiles = tiles;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public Hypothesis(Nurikabe nurikabe, Level level, TileColor startColor) {
        this(nurikabe, level.getSolution().extractNumbers(startColor));
    }

    @NotNull
    @Override
    public MutableTiles getTiles() {
        return tiles;
    }

    @NotNull
    @Override
    public Stack<Move> getUndoStack() {
        return undoStack;
    }

    @NotNull
    @Override
    public Stack<Move> getRedoStack() {
        return redoStack;
    }

    @Override
    public void touch(int col, int row) {

    }

    @Override
    public void undo() {
        //TODO
    }

    @Override
    public void redo() {
        //TODO
    }

    public Hypothesis copy() {
        //TODO copier l'hypothèse, tu peux construire une nouvelle hypothèse avec le premier constructeur, avec la même instance de Nurikabe
        // Puis copier les cases actuelles avec Tiles#copy
        // Et ensuite copier les piles undo/redo avec Stack#clone
        throw new UnsupportedOperationException();
    }

    private void forwardMove(int col, int row) {
        final Tile currentTile = tiles.get(col, row);
        tiles.set(col, row, getNextTile(currentTile));
    }

    private void backwardMove(int col, int row) {
        final Tile currentTile = tiles.get(col, row);
        tiles.set(col, row, getPreviousTile(currentTile));
    }

    @NotNull
    private static Tile getNextTile(@NotNull Tile currentTile) {
        if (currentTile instanceof NormalTile tile) {
            return switch (tile.getColor()) {
                case BLACK -> new DotTile();
                case WHITE -> new NormalTile(TileColor.BLACK);
            };
        } else if (currentTile instanceof DotTile) {
            return new NormalTile(TileColor.WHITE);
        } else {
            throw new IllegalArgumentException("Unknown tile type: " + currentTile);
        }
    }

    @NotNull
    private static Tile getPreviousTile(@NotNull Tile currentTile) {
        if (currentTile instanceof NormalTile tile) {
            return switch (tile.getColor()) {
                case BLACK -> new NormalTile(TileColor.WHITE);
                case WHITE -> new DotTile();
            };
        } else if (currentTile instanceof DotTile) {
            return new NormalTile(TileColor.BLACK);
        } else {
            throw new IllegalArgumentException("Unknown tile type: " + currentTile);
        }
    }
}
