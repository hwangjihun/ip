package hunnie.gui;

import hunnie.Hunnie;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * JavaFX entry point for the Hunnie GUI.
 */
public class Main extends Application {
    private final Hunnie hunnie = new Hunnie("src/main/data/hunnie.txt", true);

    /**
     * Starts the JavaFX application.
     *
     * @param stage Primary stage for this application.
     * @throws Exception If the FXML fails to load.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/view/style.css").toExternalForm());

        stage.setTitle("Hunnie");
        stage.setScene(scene);

        MainWindow controller = fxmlLoader.getController();
        controller.setHunnie(hunnie);

        stage.show();
    }
}
