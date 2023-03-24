/**
 * Fichier FXUtils.java
 */

// Package GitHub
package io.github.nurikabe;

// Importation des librairies javaFX
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

/**
 * Classe FXUtils
 */
public class FXUtils {
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
