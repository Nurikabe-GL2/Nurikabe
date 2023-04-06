package io.github.nurikabe;

import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

/**
 * Classe utilitaire pour JavaFX
 */
public class FXUtils {
    /**
     * Fait en sorte à ce que ce {@link ToggleGroup} ne puisse sélectionner que 1 choix à la fois.
     *
     * @param group Le groupe à modifier
     */
    public static void singleItemToggleGroup(ToggleGroup group) {
        for (Toggle toggle : group.getToggles()) {
            ((Node) toggle).addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
                // Ne pas désactiver ce bouton
                if (toggle == group.getSelectedToggle()) {
                    e.consume();
                }
            });
        }
    }
}
