package io.github.nurikabe.backend;

import io.github.nurikabe.backend.level.Adventure;
import io.github.nurikabe.backend.serialization.Saves;
import org.jetbrains.annotations.NotNull;

public class AdventureState {
    private final Adventure adventure;
    private int levelIndex = 0;

    public AdventureState(Adventure adventure, int levelIndex) {
        this.adventure = adventure;
        this.levelIndex = levelIndex;
    }

    public boolean isLastLevel() {
        return adventure.getSize() == levelIndex - 1;
    }

    @NotNull
    public static AdventureState fromNewAdventure(@NotNull Adventure adventure) {
        return new AdventureState(adventure, 0);
    }

    @NotNull
    public static AdventureState fromSavedAdventure(@NotNull Adventure adventure) {
        return Saves.load(adventure);
    }
}
