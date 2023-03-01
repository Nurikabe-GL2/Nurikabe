package io.github.nurikabe.backend;

import com.google.gson.Gson;
import io.github.nurikabe.backend.tile.TileColor;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Settings {
    private static final Path SETTINGS_PATH = Path.of(System.getProperty("user.home"), "Documents", "Nurikabe-Remake", "Settings.json");
    private static final Gson GSON = new Gson();
    private static Settings settings;

    private TileColor startColor = TileColor.WHITE;
    private boolean pathNumbering, showErrors, autocompleteOneTile, autocompleteAdjacentTiles;

    private Settings() {}

    public TileColor getStartColor() {
        return startColor;
    }

    public void setStartColor(TileColor startColor) {
        this.startColor = startColor;
        save();
    }

    public boolean isPathNumbering() {
        return pathNumbering;
    }

    public void setPathNumbering(boolean pathNumbering) {
        this.pathNumbering = pathNumbering;
        save();
    }

    public boolean isShowErrors() {
        return showErrors;
    }

    public void setShowErrors(boolean showErrors) {
        this.showErrors = showErrors;
        save();
    }

    public boolean isAutocompleteOneTile() {
        return autocompleteOneTile;
    }

    public void setAutocompleteOneTile(boolean autocompleteOneTile) {
        this.autocompleteOneTile = autocompleteOneTile;
        save();
    }

    public boolean isAutocompleteAdjacentTiles() {
        return autocompleteAdjacentTiles;
    }

    public void setAutocompleteAdjacentTiles(boolean autocompleteAdjacentTiles) {
        this.autocompleteAdjacentTiles = autocompleteAdjacentTiles;
        save();
    }

    private void save() {
        try {
            Files.createDirectories(SETTINGS_PATH.getParent());
            Files.writeString(SETTINGS_PATH, GSON.toJson(this));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Settings getSettings() {
        if (settings == null) {
            try {
                if (Files.exists(SETTINGS_PATH)) {
                    settings = GSON.fromJson(Files.readString(SETTINGS_PATH), Settings.class);
                } else {
                    settings = new Settings();
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return settings;
    }

    public static void reset() {
        settings = new Settings();
    }
}
