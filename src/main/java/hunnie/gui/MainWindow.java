package hunnie.gui;

import hunnie.Hunnie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Controller for the main window of the GUI.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Hunnie hunnie;

    private final Image userImage = new Image(getClass().getResourceAsStream("/images/user.png"));
    private final Image hunnieImage = new Image(getClass().getResourceAsStream("/images/hunnie.jpg"));

    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Hunnie instance to interact with.
     *
     * @param hunnie Hunnie instance backing this GUI.
     */
    public void setHunnie(Hunnie hunnie) {
        this.hunnie = hunnie;
        dialogContainer.getChildren().add(DialogBox.getHunnieDialog(hunnie.getWelcomeMessage(), hunnieImage));
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            return;
        }
        String response = hunnie.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getHunnieDialog(response, hunnieImage)
        );
        userInput.clear();

        if (hunnie.isExit()) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }
}
