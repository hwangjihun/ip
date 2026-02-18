package hunnie.gui;

import hunnie.Hunnie;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main window of the GUI.
 */
public class MainWindow {
    private static final int DIALOG_ANIMATION_DURATION_MS = 220;
    private static final int USER_DIALOG_DELAY_MS = 0;
    private static final int HUNNIE_DIALOG_DELAY_MS = 75;
    private static final double DIALOG_START_OFFSET_Y = 8.0;

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
        DialogBox welcomeDialog = DialogBox.getHunnieDialog(hunnie.getWelcomeMessage(), hunnieImage);
        dialogContainer.getChildren().add(welcomeDialog);
        playDialogEntryAnimation(welcomeDialog, USER_DIALOG_DELAY_MS);
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.trim().isEmpty()) {
            return;
        }
        String response = hunnie.getResponse(input);
        DialogBox userDialog = DialogBox.getUserDialog(input, userImage);
        DialogBox hunnieDialog = DialogBox.getHunnieDialog(response, hunnieImage);
        dialogContainer.getChildren().addAll(userDialog, hunnieDialog);
        playDialogEntryAnimation(userDialog, USER_DIALOG_DELAY_MS);
        playDialogEntryAnimation(hunnieDialog, HUNNIE_DIALOG_DELAY_MS);
        userInput.clear();

        if (hunnie.isExit()) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
            // This exit-delay implementation was written with the help of ChatGPT.
            PauseTransition exitDelay = new PauseTransition(Duration.millis(Hunnie.EXIT_DELAY_MS));
            exitDelay.setOnFinished(event -> Platform.exit());
            exitDelay.play();
        }
    }

    private void playDialogEntryAnimation(Node dialogNode, int delayMs) {
        // The fade/slide animation setup below was written with the help of ChatGPT.
        // In addition, I have prompted ChatGPT to customize style.css to enhance the
        // overall UI experience based on the theme that I have chosen.
        dialogNode.setOpacity(0);
        dialogNode.setTranslateY(DIALOG_START_OFFSET_Y);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(DIALOG_ANIMATION_DURATION_MS), dialogNode);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setDelay(Duration.millis(delayMs));

        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(DIALOG_ANIMATION_DURATION_MS), dialogNode);
        translateTransition.setFromY(DIALOG_START_OFFSET_Y);
        translateTransition.setToY(0);
        translateTransition.setDelay(Duration.millis(delayMs));

        ParallelTransition dialogReveal = new ParallelTransition(fadeTransition, translateTransition);
        dialogReveal.play();
    }
}
