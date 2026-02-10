package hunnie.gui;

import javafx.application.Application;

/**
 * Separate launcher class to work around JavaFX packaging requirements.
 */
public class Launcher {
    /**
     * Launches the JavaFX application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
